package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
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
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        final int varId = getVariableId( instruction );
        final String name = context.getLocalVariable( varId );
        if ( context.getScopeDefinedVars().add( name ) ) {
            writer.writeln( level, context.getLineNumber( instruction ), context.getLocalVariableType( varId, writer ) + " " + name + " = " + stack.pop().getValue() + ";" );
        } else {
            writer.writeln( level, context.getLineNumber( instruction ), name + " = " + stack.pop().getValue() + ";" );
        }
    }

    public int getVariableId( Instruction instruction ) {
        return instruction.getData()[0];
    }

    public static final class StoreSpecShort extends StoreSpec {

        private final int id;

        public StoreSpecShort( String mnemonic, int typeId, int dataLen, Integer id ) {
            super( mnemonic, typeId, dataLen );
            this.id = id;
        }

        @Override
        public int getVariableId( Instruction instruction ) {
            return this.id;
        }
    }

}
