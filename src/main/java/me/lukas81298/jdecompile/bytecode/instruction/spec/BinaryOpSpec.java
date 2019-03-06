package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 04.03.2019
 */
public class BinaryOpSpec extends InstructionSpec {

    private final String seperator;

    public BinaryOpSpec( String mnemonic, int typeId, int dataLen, String seperator ) {
        super( mnemonic, typeId, dataLen );
        this.seperator = seperator;
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        Operand o1 = stack.pop();
        Operand o2 = stack.pop();
        OperandType type = o1.getType();
        stack.push( new Operand( type, o1.getValue() + " " + seperator + " " + o2.getValue() ) );
    }
}
