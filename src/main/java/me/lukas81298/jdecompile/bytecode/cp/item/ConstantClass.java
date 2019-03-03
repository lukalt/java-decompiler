package me.lukas81298.jdecompile.bytecode.cp.item;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPoolItem;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 02.03.2019
 */
public class ConstantClass extends ConstantPoolItem {

    private int nameIndex;

    public ConstantClass( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException {
        this.nameIndex = reader.readUnsignedShort();
    }

    public String getClassName() throws DecompileException {
        return this.constantPool.getUtf8( this.nameIndex );
    }

    @Override
    public String asString() throws DecompileException {
        return this.getClassName();
    }
}
