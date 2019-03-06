package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class ArrLoadSpec extends InstructionSpec {

    private final OperandType type;

    public ArrLoadSpec( String mnemonic, int typeId, int dataLen, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.type = type;
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        stack.push( new Operand( this.type, stack.pop().getValue() + "[" + stack.pop().getValue() + "]" ) );
    }
}
