package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;

import java.util.Stack;

/**
 * @author lukas
 * @since 07.03.2019
 */
public class SimpleIfSpec extends AbstractIfSpec {

    private final String operation;

    public SimpleIfSpec( String mnemonic, int typeId, int dataLen, String operation ) {
        super( mnemonic, typeId, dataLen );
        this.operation = operation;
    }

    @Override
    public Object[] getParams( Stack<Operand> stack ) {
        return new Object[] { stack.pop().getValue() };
    }

    @Override
    public String getCondition( Instruction instruction ) {
        return "%s " + this.operation;
    }
}
