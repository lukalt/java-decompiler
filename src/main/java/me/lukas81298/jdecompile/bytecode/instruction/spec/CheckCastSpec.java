package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class CheckCastSpec extends InstructionSpec {

    public CheckCastSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        stack.push( new Operand( OperandType.REFERENCE, "((" + context.getConstantPool().getItem( instruction.getUnsignedShort( 0 ) ) + ") " + stack.pop() + ")" ) );
    }
}
