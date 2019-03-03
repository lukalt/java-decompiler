package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.InstructionSpec;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;

import java.util.Stack;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class StoreSpec extends InstructionSpec {

    public StoreSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        writer.writeln( level, context.getLocalVariable( getVariableId( instruction ) ) + " = " + stack.pop().getValue() + ";" );
    }

    public int getVariableId( Instruction instruction ) {
        return instruction.getData()[0];
    }

    public static final class StoreSpecShort extends StoreSpec {

        private final int id;

        public StoreSpecShort( int id, String mnemonic, int typeId, int dataLen ) {
            super( mnemonic, typeId, dataLen );
            this.id = id;
        }

        @Override
        public int getVariableId( Instruction instruction ) {
            return this.id;
        }
    }

}
