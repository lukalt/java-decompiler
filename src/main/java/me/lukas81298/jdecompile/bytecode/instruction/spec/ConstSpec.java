package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class ConstSpec extends InstructionSpec {

    private final Object value;
    private final OperandType type;

    public ConstSpec( String mnemonic, int typeId, int dataLen, String value, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.value = value;
        this.type = type;
    }

    public ConstSpec( String mnemonic, int typeId, int dataLen, Integer value, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.value = value;
        this.type = type;
    }

    public ConstSpec( String mnemonic, int typeId, int dataLen, Float value, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.value = value;
        this.type = type;
    }

    public ConstSpec( String mnemonic, int typeId, int dataLen, Long value, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.value = value;
        this.type = type;
    }

    public ConstSpec( String mnemonic, int typeId, int dataLen, Double value, OperandType type ) {
        super( mnemonic, typeId, dataLen );
        this.value = value;
        this.type = type;
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        stack.push( new Operand( type, value ) );
    }
}
