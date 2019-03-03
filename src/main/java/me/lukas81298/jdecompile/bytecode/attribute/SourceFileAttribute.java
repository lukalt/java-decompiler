package me.lukas81298.jdecompile.bytecode.attribute;

import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 03.03.2019
 */
@Getter
public class SourceFileAttribute extends Attribute {

    private String fileName;

    public SourceFileAttribute( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException, DecompileException {
        this.fileName = this.constantPool.getUtf8( reader.readUnsignedShort() );
    }
}
