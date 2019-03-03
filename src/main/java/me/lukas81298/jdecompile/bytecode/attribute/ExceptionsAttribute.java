package me.lukas81298.jdecompile.bytecode.attribute;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantClass;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class ExceptionsAttribute extends Attribute {

    @Getter
    private ConstantClass[] exceptions;

    public ExceptionsAttribute( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException, DecompileException {
        this.exceptions = new ConstantClass[reader.readUnsignedShort()];
        for ( int i = 0; i < this.exceptions.length; i++ ) {
            this.exceptions[i] = constantPool.getItem( reader.readUnsignedShort() );
        }
    }
}
