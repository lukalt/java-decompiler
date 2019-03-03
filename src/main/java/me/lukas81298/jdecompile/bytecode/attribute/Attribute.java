package me.lukas81298.jdecompile.bytecode.attribute;

import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 03.03.2019
 */
@RequiredArgsConstructor
public abstract class Attribute {

    protected final ConstantPool constantPool;

    public abstract void read( ByteCodeReader reader ) throws IOException, DecompileException;

}
