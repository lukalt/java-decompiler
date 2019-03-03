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
public abstract class AbstractConstantRef extends ConstantPoolItem {

    private int classIndex;
    private int nameTypeIndex;

    public AbstractConstantRef( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws DecompileException, IOException {
        this.classIndex = reader.readUnsignedShort();
        this.nameTypeIndex = reader.readUnsignedShort();
    }

    public ConstantClass getClassInfo() throws DecompileException {
        return this.constantPool.getItem( this.classIndex );
    }

    public ConstantNameAndType getNameAndType() throws DecompileException {
        return this.constantPool.getItem( this.nameTypeIndex );
    }

    @Override
    public String asString() throws DecompileException {
        return this.getClassInfo().asString() + "#" + this.getNameAndType().asString();
    }

    public static class ConstantMethodRef extends AbstractConstantRef {

        public ConstantMethodRef( ConstantPool constantPool ) {
            super( constantPool );
        }
    }

    public static class ConstantInterfaceMethodRef extends AbstractConstantRef {

        public ConstantInterfaceMethodRef( ConstantPool constantPool ) {
            super( constantPool );
        }
    }

    public static class ConstantFieldRef extends AbstractConstantRef {

        public ConstantFieldRef( ConstantPool constantPool ) {
            super( constantPool );
        }
    }
}
