package me.lukas81298.jdecompile.bytecode.flow.struct;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;

import java.util.Stack;

/**
 * @author lukas
 * @since 09.03.2019
 */
@Getter
public class InstructionStructure extends Structure {

    private final Instruction instruction;

    public InstructionStructure( int level, Instruction instruction ) {
        super( level );
        this.instruction = instruction;
    }

    @Override
    public void process( Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        this.instruction.getInstructionSpec().process( this.level, this.instruction, stack, writer, context );
    }

    @Override
    public String toString() {
        return this.instruction.toString();
    }
}
