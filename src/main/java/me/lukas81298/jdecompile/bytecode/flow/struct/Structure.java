package me.lukas81298.jdecompile.bytecode.flow.struct;

import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;

import java.util.Stack;

/**
 * @author lukas
 * @since 09.03.2019
 */
@RequiredArgsConstructor
public abstract class Structure {

    protected final int level;

    public abstract void process( Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException;

}
