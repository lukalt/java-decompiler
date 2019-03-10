package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;

import java.util.Stack;

/**
 * @author lukas
 * @since 07.03.2019
 */
public class BinIfSpec extends AbstractIfSpec {

    private final String op;

    public BinIfSpec( String mnemonic, int typeId, int dataLen, String op ) {
        super( mnemonic, typeId, dataLen );
        this.op = op;
    }


    @Override
    public Object[] getParams( Stack<Operand> stack ) {
        final Object value = stack.pop().getValue();
        return new Object[] { stack.pop().getValue(), value };
    }

    @Override
    public String getCondition( Instruction instruction ) {
        return "%s " + op + " %s";
    }
}
