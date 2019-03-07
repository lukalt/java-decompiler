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
 * @since 06.03.2019
 */
public class IincSpec extends InstructionSpec {

    public IincSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        int v = instruction.getData()[1];
        if(v == 1) {
            writer.writeln( level, context.getLocalVariable( instruction.getData()[0] ) + "++" );
        } else {
            writer.writeln( level, context.getLocalVariable( instruction.getData()[0] ) + " += " + v );
        }
    }
}
