package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 07.03.2019
 */
public class SiPushSpec extends InstructionSpec {

    public SiPushSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        stack.push( new Operand( OperandType.SHORT, instruction.getUnsignedShort( 0 ) ) );
    }
}
