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
public class PopSpec extends InstructionSpec {

    public PopSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) {
        stack.pop();
    }
}
