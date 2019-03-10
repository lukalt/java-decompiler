package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.InstructionSpec;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;

import java.util.Stack;

/**
 * @author lukas
 * @since 07.03.2019
 */
public abstract class AbstractIfSpec extends InstructionSpec {

    public AbstractIfSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
       // writer.writeln( level, "if(" + String.format( getCondition( instruction ), getParams( stack ) ) + ") {" );
       // writer.writeln( level, "}" );
    }

    public abstract Object[] getParams( Stack<Operand> stack );

    public abstract String getCondition( Instruction instruction );

    public int getBranch( Instruction instruction ) {
        if( !instruction.getInstructionSpec().equals( this ) ) {
            throw new IllegalStateException( "Mismatch: " + instruction.getInstructionSpec().getClass().getName() + " " + this.getClass().getName() );
        }
        return instruction.getUnsignedShort( 0 );
    }
}
