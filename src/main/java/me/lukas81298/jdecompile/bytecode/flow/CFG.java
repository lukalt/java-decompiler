package me.lukas81298.jdecompile.bytecode.flow;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.stack.TIntStack;
import gnu.trove.stack.array.TIntArrayStack;
import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AReturnSpec;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AbstractGotoSpec;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AbstractIfSpec;
import me.lukas81298.jdecompile.bytecode.instruction.spec.ReturnSpec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lukas
 * @since 07.03.2019
 */
@Getter
public class CFG {

    private final Instruction[] instructions;
    private final TIntList[] adj;

    public CFG( Collection<Instruction> iterable ) throws DecompileException {
        if ( iterable.isEmpty() ) {
            throw new DecompileException( "Cannot construct cfg for empty instruction list!" );
        }
        int highestPc = 0;
        for ( Instruction instruction : iterable ) {
            if ( instruction.getPc() > highestPc ) {
                highestPc = instruction.getPc();
            }
        }
        highestPc++;
        this.instructions = new Instruction[highestPc];
        this.adj = new TIntList[highestPc + 1];
        Instruction last = null;
        for ( Instruction instruction : iterable ) {
            this.instructions[instruction.getPc()] = instruction;

            if ( last != null ) {
                TIntList tIntList = this.adj[last.getPc()];
                if ( tIntList == null ) {
                    tIntList = this.adj[last.getPc()] = new TIntArrayList();
                }
                if ( last.getInstructionSpec() instanceof ReturnSpec || last.getInstructionSpec() instanceof AReturnSpec ) {
                    tIntList.add( highestPc );
                } else {
                    if ( last.getInstructionSpec() instanceof AbstractGotoSpec ) {
                        tIntList.add( last.getPc() + ( (AbstractGotoSpec) last.getInstructionSpec() ).getBranch( last ) );
                    } else {
                        tIntList.add( instruction.getPc() );
                        if ( last.getInstructionSpec() instanceof AbstractIfSpec ) {
                            final int i = last.getPc() + ( (AbstractIfSpec) last.getInstructionSpec() ).getBranch( last );
                            if ( i == this.adj.length + 1 ) {
                                tIntList.add( this.adj.length - 1 );
                            } else {
                                tIntList.add( i );
                            }
                        }
                    }
                }
            }
            last = instruction;

            if ( instruction.isLast() ) {
                if ( this.adj[instruction.getPc()] == null ) {
                    this.adj[instruction.getPc()] = new TIntArrayList();
                }
                this.adj[instruction.getPc()].add( highestPc );
            }
        }

    }

    private enum DFSColor {
        WHITE,
        GRAY,
        BLACK
    }

    public List<TIntList> findSccs() {
        DFSColor[] color = new DFSColor[adj.length];
        for ( int i = 0; i < color.length; i++ ) {
            color[i] = DFSColor.WHITE;
        }
        TIntStack stack = new TIntArrayStack();
        int[] scc = new int[adj.length];
        for ( int i = 0; i < adj.length; i++ ) {
            if ( color[i] == DFSColor.WHITE ) {
                dfs1( adj, i, color, stack );
            }
        }
        TIntList[] transposed = new TIntList[adj.length];
        for ( int i = 0; i < adj.length; i++ ) {
            final TIntList li = adj[i];
            if( li != null ) {
                for ( int w : li.toArray() ) {
                    TIntList l = transposed[w];
                    if ( l == null ) {
                        l = transposed[w] = new TIntArrayList();
                    }
                    l.add( i );
                }
            }
        }
        for ( int i = 0; i < color.length; i++ ) {
            color[i] = DFSColor.WHITE;
        }
        while ( stack.size() > 0 ) {
            int i = stack.pop();
            if ( color[i] == DFSColor.WHITE ) {
                dfs2( transposed, i, color, i, scc );
            }
        }
        List<TIntList> list = new ArrayList<>();
        for ( int i = 0; i < scc.length; i++ ) {
            if ( scc[i] < 0 ) {
                continue;
            }
            TIntList l = new TIntArrayList();
            l.add( i );
            for ( int j = i; j < scc.length - 1; j++ ) {
                if ( j != i ) {
                    if ( scc[i] == scc[j] ) {
                        l.add( j );
                        scc[j] = -1;
                    }
                }
            }
            if ( l.size() > 1 ) {
                list.add( l );
            }
        }
        return list;
    }

    private void dfs1( TIntList[] adj, int start, DFSColor[] color, TIntStack stack ) {
        color[start] = DFSColor.GRAY;
        final TIntList l = adj[start];
        if ( l != null ) {
            for ( int w : l.toArray() ) {
                if ( color[w] == DFSColor.WHITE ) {
                    dfs1( adj, w, color, stack );
                }
            }
        }
        color[start] = DFSColor.BLACK;
        stack.push( start );
    }

    private void dfs2( TIntList[] adj, int start, DFSColor[] color, int leader, int[] scc ) {
        color[start] = DFSColor.GRAY;
        final TIntList l = adj[start];
        if ( l != null ) {
            for ( int w : l.toArray() ) {
                if ( color[w] == DFSColor.WHITE ) {
                    dfs2( adj, w, color, leader, scc );
                }
            }
        }
        color[start] = DFSColor.BLACK;
        scc[start] = leader;
    }

    public String toAdjMatrix() {
        StringBuilder builder = new StringBuilder();
        for ( TIntList list : this.adj ) {
            if ( builder.length() > 0 ) {
                builder.append( '\n' );
            }
            for ( int i = 0; i < this.adj.length; i++ ) {
                if ( i > 0 ) {
                    builder.append( ' ' );
                }
                builder.append( list.contains( i ) ? '1' : '0' );
            }
        }
        return builder.toString();
    }
}
