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
public class ConstantNameAndType extends ConstantPoolItem {

    private int nameIndex, typeIndex;

    public ConstantNameAndType( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws DecompileException, IOException {
        this.nameIndex = reader.readUnsignedShort();
        this.typeIndex = reader.readUnsignedShort();
    }

    public String getName() throws DecompileException {
        return this.constantPool.getUtf8( this.nameIndex );
    }

    public String getType() throws DecompileException {
        return this.constantPool.getUtf8( this.typeIndex );
    }

    @Override
    public String asString() throws DecompileException {
        return this.getName() + ":" + this.getType();
    }
}
