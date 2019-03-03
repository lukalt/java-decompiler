package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class LoadSpec extends InstructionSpec {

    public LoadSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        stack.push( new Operand( OperandType.REFERENCE, context.getLocalVariable( getVariableId( instruction ) ) ) );
    }

    public int getVariableId( Instruction instruction ) {
        return instruction.getData()[0];
    }

    public static final class LoadSpecShort extends LoadSpec {

        private final int id;

        public LoadSpecShort( String mnemonic, int typeId, int dataLen, int id ) {
            super( mnemonic, typeId, dataLen );
            this.id = id;
        }

        @Override
        public int getVariableId( Instruction instruction ) {
            return this.id;
        }
    }
}
