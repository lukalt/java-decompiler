package me.lukas81298.jdecompile.bytecode.cp.item;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPoolItem;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class ConstantMethodType extends ConstantPoolItem {

    private int descriptorIndex;

    public ConstantMethodType( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws DecompileException, IOException {
        this.descriptorIndex = reader.readUnsignedShort();
    }

    public String getMethodDescriptor() throws DecompileException {
        return this.constantPool.getUtf8( this.descriptorIndex );
    }

    @Override
    public String asString() throws DecompileException {
        return this.getMethodDescriptor();
    }
}
