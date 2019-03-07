package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class PrimitiveCastSpec extends InstructionSpec {

    private final OperandType type;

    public PrimitiveCastSpec( String mnemonic, int typeId, int dataLen, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.type = type;
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        stack.push( new Operand( this.type, "((" + this.type.getType() + ") " + stack.pop().getValue() + ")" ) );
    }
}
