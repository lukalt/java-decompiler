package me.lukas81298.jdecompile.bytecode.cp.item;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPoolItem;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 02.03.2019
 */
public class ConstantMethodHandle extends ConstantPoolItem {

    public enum Type {
        GET_FIELD,
        GET_STATIC,
        PUT_FIELD,
        PUT_STATIC,
        INVOKE_VIRTUAL,
        INVOKE_STATIC,
        INVOKE_SPECIAL,
        NEW_INVOKE_SPECIAL,
        REF_INVOKE_INTERFACE
    }

    @Getter
    private Type type;
    private int referenceIndex;

    public ConstantMethodHandle( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws DecompileException, IOException {
        this.type = Type.values()[reader.readUnsignedByte() - 1];
        this.referenceIndex = reader.readUnsignedShort();
    }

    public <K extends ConstantPoolItem> K getReference() throws DecompileException {
        return this.constantPool.getItem( this.referenceIndex );
    }

    @Override
    public String asString() throws DecompileException {
        return this.type.name() + ":" + this.getReference().asString();
    }
}
