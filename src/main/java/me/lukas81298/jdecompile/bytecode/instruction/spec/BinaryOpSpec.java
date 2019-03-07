package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 04.03.2019
 */
public class BinaryOpSpec extends InstructionSpec {

    private final String separator;

    public BinaryOpSpec( String mnemonic, int typeId, int dataLen, String separator ) {
        super( mnemonic, typeId, dataLen );
        this.separator = separator;
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        Operand o1 = stack.pop();
        Operand o2 = stack.pop();
        OperandType type = o2.getType();
        stack.push( new Operand( type, o2.getValue() + " " + separator + " " + o1.getValue() ) );
    }
}
