package me.lukas81298.jdecompile.bytecode.cp.item;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPoolItem;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class ConstantInvokeDynamic extends ConstantPoolItem {

    @Getter
    private int bootstrapIndex;
    private int nameAndTypeIndex;

    public ConstantInvokeDynamic( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws DecompileException, IOException {
        this.bootstrapIndex = reader.readUnsignedShort();
        this.nameAndTypeIndex = reader.readUnsignedShort();
    }

    public ConstantNameAndType getType() throws DecompileException {
        return this.getConstantPool().getItem( this.nameAndTypeIndex );
    }

    @Override
    public String asString() throws DecompileException {
        return this.bootstrapIndex + "$" + this.getType().asString();
    }

    @Override
    public byte getSize() {
        return 2;
    }
}
