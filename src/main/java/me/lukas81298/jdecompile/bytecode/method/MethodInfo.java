package me.lukas81298.jdecompile.bytecode.method;

import gnu.trove.list.TIntList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import me.lukas81298.jdecompile.CodeWriteable;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.AccessFlag;
import me.lukas81298.jdecompile.bytecode.Attributable;
import me.lukas81298.jdecompile.bytecode.ClassFile;
import me.lukas81298.jdecompile.bytecode.attribute.Attribute;
import me.lukas81298.jdecompile.bytecode.attribute.CodeAttribute;
import me.lukas81298.jdecompile.bytecode.attribute.ExceptionsAttribute;
import me.lukas81298.jdecompile.bytecode.attribute.LocalVariableTableAttribute;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantClass;
import me.lukas81298.jdecompile.bytecode.flow.CFG;
import me.lukas81298.jdecompile.bytecode.flow.InstructionEntry;
import me.lukas81298.jdecompile.bytecode.flow.struct.*;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AbstractGotoSpec;
import me.lukas81298.jdecompile.bytecode.instruction.spec.AbstractIfSpec;

import java.util.*;

/**
 * @author lukas
 * @since 02.03.2019
 */
@Getter
public class MethodInfo implements CodeWriteable, Attributable {

    private final Set<AccessFlag> flags;
    private final String name;
    private final String descriptor;
    private final Map<String, Attribute> attributes;
    private final ClassFile classFile;
    private final MethodType type;

    public MethodInfo( Set<AccessFlag> flags, String name, String descriptor, Map<String, Attribute> attributes, ClassFile classFile ) {
        this.flags = flags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = attributes;
        this.classFile = classFile;
        this.type = MethodType.byMethodName( this.name );
    }

    private String getSignature( SourceCodeWriter writer ) throws DecompileException {
        if ( this.type == MethodType.CLASS_CONSTRUCTOR ) {
            return "static {";
        }
        StringBuilder buf = new StringBuilder();
        String mod = this.formatAccessModifier();
        if ( !mod.isEmpty() ) {
            buf.append( mod ).append( " " );
        }
        if ( this.flags.contains( AccessFlag.ACC_STATIC ) ) {
            buf.append( "static " );
        }
        if ( this.flags.contains( AccessFlag.ACC_FINAL ) ) {
            buf.append( "final " );
        }
        if ( this.flags.contains( AccessFlag.ACC_ABSTRACT ) ) {
            buf.append( "abstract " );
        }
        List<String> params = new ArrayList<>();
        String returnType = writer.parseMethodDescriptor( this.descriptor, params );
        if ( this.type == MethodType.METHOD ) {
            buf.append( returnType ).append( " " );
            buf.append( this.name );
        } else {
            buf.append( writer.formatClassName( this.classFile.getClassName() ) );
        }
        buf.append( '(' );
        int j = 0;
        for ( String param : params ) {
            if ( j > 0 ) {
                buf.append( ", " );
            }

            CodeAttribute codeAttribute;
            if ( this.hasAttribute( "Code" ) && ( codeAttribute = this.getAttribute( "Code" ) ).hasAttribute( "LocalVariableTable" ) ) {
                LocalVariableTableAttribute localVariableTableAttribute = codeAttribute.getAttribute( "LocalVariableTable" );
                buf.append( param ).append( " " ).append( localVariableTableAttribute.getParameterName( j ) );
            } else {
                buf.append( param ).append( " p" ).append( j );
            }
            j++;
        }
        buf.append( ')' );
        if ( this.hasAttribute( "Exceptions" ) ) {
            ExceptionsAttribute attribute = this.getAttribute( "Exceptions" );
            if( attribute.getExceptions().length > 0 ) {
                int i = 0;
                for ( ConstantClass exception : attribute.getExceptions() ) {
                    if( i > 0 ) {
                        buf.append( ", " );
                    }
                    buf.append( writer.formatClassName( exception.getClassName() ) );
                    i++;
                }
            }
        }
        return buf.toString();
    }

    public boolean isSynthetic() {
        return this.flags.contains( AccessFlag.ACC_SYNTHETIC ) || this.hasAttribute( "Synthetic" );
    }

    private List<Structure> preprocess( int level, List<Instruction> instructions ) throws DecompileException {
        CFG cfg = new CFG( instructions );
        TIntIntMap subBlocksStart = new TIntIntHashMap();

        for ( TIntList c : cfg.findSccs() ) {
            c.sort();
            subBlocksStart.put( c.get( 0 ), c.get( c.size() - 1 ) );
        }

        List<Structure> out = new LinkedList<>();

        int untilLoop = -1;
        int ifEndPc = -1;
        int elseEndPc = -1;
        List<Instruction> currentInstructions = new ArrayList<>();
        List<Instruction> ifInstructions = new LinkedList<>();
        List<Instruction> elseInstructions = new LinkedList<>();

        ListIterator<Instruction> iterator = instructions.listIterator();

       while ( iterator.hasNext() ) {
           Instruction instruction = iterator.next();
            if ( untilLoop >= 0 && instruction.getPc() <= untilLoop ) {
                currentInstructions.add( instruction );
                continue;
            } else if ( untilLoop != -1 ) {
                untilLoop = -1;

                List<InstructionEntry> conditions = new ArrayList<>();
                List<Instruction> instructionBuffer = new LinkedList<>();

                // while loop structure:
                // - load arguments onto stack
                // - check condition with arguments
                // - check if there are more conditions!
                // - execute code
                int target = -1;
                for ( Instruction currentInstruction : currentInstructions ) {
                    if ( currentInstruction.getInstructionSpec() instanceof AbstractIfSpec ) {
                        int c = currentInstruction.getPc() + ( (AbstractIfSpec) currentInstruction.getInstructionSpec() ).getBranch( currentInstruction );
                        if( target == -1 || target == c ) {
                            target = c;
                            conditions.add( new InstructionEntry( instructionBuffer, currentInstruction ) );
                            instructionBuffer = new LinkedList<>();
                        } else {
                            instructionBuffer.add( currentInstruction );
                        }
                    } else {
                        instructionBuffer.add( currentInstruction );
                    }
                }
                // recursively preprocess the underlying group of instructions
                out.add( new WhileStructure( level, conditions, preprocess( level + 1, instructionBuffer ) ) );
                currentInstructions.clear();
            }
            if ( subBlocksStart.containsKey( instruction.getPc() ) ) {
                untilLoop = subBlocksStart.get( instruction.getPc() );
                currentInstructions.add( instruction );
            } else {
                // check for if blocks
                if ( instruction.getInstructionSpec() instanceof AbstractIfSpec ) { // start a new if block
                    ifEndPc = instruction.getPc() + ( (AbstractIfSpec) instruction.getInstructionSpec() ).getBranch( instruction );
                    ifInstructions.add( instruction );
                } else if ( ifEndPc != -1 && instruction.getPc() < ifEndPc ) { // block still "active"?
                    if( instruction.getInstructionSpec() instanceof AbstractGotoSpec
                            && iterator.hasNext() && instructions.get( iterator.nextIndex() ).getPc() == ifEndPc ) {
                        elseEndPc = instruction.getPc() + ( (AbstractGotoSpec) instruction.getInstructionSpec() ).getBranch( instruction );
                    } else {
                        ifInstructions.add( instruction );
                    }
                } else if( ifEndPc != -1 ) {
                    ifEndPc = -1;
                    if( elseEndPc != -1 ) {
                        elseInstructions.add( instruction );
                    } else {
                        iterator.previous(); // run the loop on this instruction again!
                        IfStructure structure = new IfStructure( level, ifInstructions.get( 0 ), preprocess( level + 1, ifInstructions.subList( 1, ifInstructions.size() ) ) );
                        out.add( structure );
                        ifInstructions.clear();
                    }
                } else if( elseEndPc != -1 && instruction.getPc() < elseEndPc ) {
                    elseInstructions.add( instruction );
                } else if( elseEndPc != -1 ) {
                    elseInstructions.add( instruction );
                    IfElseStructure structure = new IfElseStructure( level, ifInstructions.get( 0 ),
                            preprocess( level + 1, ifInstructions.subList( 1, ifInstructions.size() ) ),
                            preprocess( level + 1, elseInstructions ) );
                    out.add( structure );
                    ifInstructions.clear();
                    elseInstructions.clear();
                } else {
                    // just a plain instruction not belonging to a structure
                    out.add( new InstructionStructure( level, instruction ) );
                }
            }

        }

        return out;
    }

    @Override
    public void write( SourceCodeWriter writer, int level ) throws DecompileException {
        if ( this.getFlags().contains( AccessFlag.ACC_SUPER ) ) {
            writer.writeln( level, "@Override" );
        }
        writer.writeln( level, getSignature( writer ) + " {" );
        if ( this.hasAttribute( "Code" ) ) {
            CodeAttribute attr = this.getAttribute( "Code" );
            try {
                final TIntObjectMap<LocalVariableTableAttribute.LocalVariable> variables;
                if ( attr.hasAttribute( "LocalVariableTable" ) ) {
                    variables = ( (LocalVariableTableAttribute) attr.getAttribute( "LocalVariableTable" ) ).getLocalVar();
                } else {
                    variables = new TIntObjectHashMap<>();
                }

                Context context = new Context( classFile.getConstantPool(), variables, this.classFile );

                Stack<Operand> stack = new Stack<>();
                List<Structure> structures = this.preprocess( level + 1, new ArrayList<>( attr.getInstructions().values() ) );
                for ( Structure structure : structures ) {
                    structure.process( stack, writer, context );
                }
                // uncomment to print control flow graph adjency matrix as a comment to the output
               /* writer.writeln( level + 1, "/*" );
                final CFG cfg = new CFG( attr.getInstructions().values() );
                for ( String s : cfg.toAdjMatrix().split( "\n" ) ) {
                    writer.writeln( level + 2, s );
                }
                writer.writeln( level, cfg.findSccs().toString() );

                writer.writeln( level + 1, "*" + "/" );*/
            } catch ( Throwable t ) {
                t.printStackTrace();
                writer.writeln( level + 1, "// Error decompiling: " + t.getMessage() );
                switch ( writer.parseMethodDescriptor( this.descriptor, new LinkedList<>() ) ) {
                    case "int":
                        writer.writeln( level + 1, "return 0;" );
                        break;
                    case "long":
                        writer.writeln( level + 1, "return 0L;" );
                        break;
                    case "double":
                        writer.writeln( level + 1, "return 0D;" );
                        break;
                    case "float":
                        writer.writeln( level + 1, "return 0F;" );
                        break;
                    case "char":
                        writer.writeln( level + 1, "return '0';" );
                        break;
                    case "boolean":
                        writer.writeln( level + 1, "return false;" );
                        break;
                    case "byte":
                        writer.writeln( level + 1, "return (byte) 0;" );
                        break;
                    default:
                        writer.writeln( level + 1, "return null;" );
                }
            }
        }
        writer.writeln( level, "}" );
        writer.writeln( level );
    }

}
