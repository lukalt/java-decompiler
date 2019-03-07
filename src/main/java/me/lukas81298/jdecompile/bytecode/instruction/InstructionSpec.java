package me.lukas81298.jdecompile.bytecode.instruction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;

import java.util.Stack;

/**
 * @author lukas
 * @since 03.03.2019
 */
@RequiredArgsConstructor
public abstract class InstructionSpec {

    @Getter
    private final String mnemonic;
    private final int typeId;
    @Getter
    private final int dataLen;

    public abstract void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException;

}
