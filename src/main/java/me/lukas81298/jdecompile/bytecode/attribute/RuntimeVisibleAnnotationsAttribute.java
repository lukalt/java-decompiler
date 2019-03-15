package me.lukas81298.jdecompile.bytecode.attribute;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 13.03.2019
 */
public class RuntimeVisibleAnnotationsAttribute extends Attribute {

    @Getter
    private Annotation[] attributes;

    public RuntimeVisibleAnnotationsAttribute( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException, DecompileException {
        this.attributes = new Annotation[reader.readUnsignedShort()];
        for ( int i = 0; i < this.attributes.length; i++ ) {
            String type = this.constantPool.getUtf8( reader.readUnsignedShort() );
            Element[] elements = new Element[reader.readUnsignedShort()];
            for ( int j = 0; j < elements.length; j++ ) {
                Element el = new Element();
                el.read( reader );
                elements[j] = el;
            }
            this.attributes[i] = new Annotation( type, elements );
        }
    }

    @RequiredArgsConstructor
    @Getter
    public final class Annotation {

        private final String type;
        private final Element[] elements;
    }

    public final class Element {

        public void read( ByteCodeReader reader ) throws IOException, DecompileException {
            int tag = reader.readUnsignedByte();

        }

    }

}
