package me.lukas81298.jdecompile.bytecode.attribute;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import lombok.Getter;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 13.03.2019
 */
public class LineNumberTableAttribute extends Attribute {

    @Getter
    private final TIntIntMap lineNumbers = new TIntIntHashMap( 10, 0.75F, 0, -1 );

    public LineNumberTableAttribute( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException, DecompileException {
        int length = reader.readUnsignedShort();
        for ( int i = 0; i < length; i++ ) {
            this.lineNumbers.put( reader.readUnsignedShort(), reader.readUnsignedShort() );
        }
    }

}
