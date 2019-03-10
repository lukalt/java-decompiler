package me.lukas81298.jdecompile.bytecode.io;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.AccessFlag;
import me.lukas81298.jdecompile.bytecode.ClassFile;
import me.lukas81298.jdecompile.bytecode.attribute.Attribute;
import me.lukas81298.jdecompile.bytecode.attribute.AttributeFactory;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.field.FieldInfo;
import me.lukas81298.jdecompile.bytecode.method.MethodInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author lukas
 * @since 02.03.2019
 */
public class ByteCodeReader extends DataInputStream {

    private final AttributeFactory attributeFactory;

    public ByteCodeReader( InputStream in, AttributeFactory attributeFactory ) {
        super( in );
        this.attributeFactory = attributeFactory;
    }

    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public ByteCodeReader( InputStream in ) {
        super( in );
        this.attributeFactory = new AttributeFactory();
    }

    /**
     * Constructs a new field info objects and deserializes it from the underlying input stream
     *
     * @param cf the corresponding class file
     * @return FieldInfo representing a field in class file
     */
    public FieldInfo readFieldInfo( ClassFile cf ) throws IOException, DecompileException {
        ConstantPool constantPool = cf.getConstantPool();
        return new FieldInfo( AccessFlag.fromBitmask( readUnsignedShort(), new HashSet<>() ),
                constantPool.getUtf8( readUnsignedShort() ),
                constantPool.getUtf8( readUnsignedShort() ),
                readAttributes( constantPool ),
                cf
        );
    }


    public MethodInfo readMethodInfo( ConstantPool constantPool, ClassFile cf ) throws IOException, DecompileException {
        return new MethodInfo(
                AccessFlag.fromBitmask( readUnsignedShort(), new HashSet<>() ),
                constantPool.getUtf8( readUnsignedShort() ),
                constantPool.getUtf8( readUnsignedShort() ),
                readAttributes( constantPool ),
                cf
        );
    }

    public Map<String, Attribute> readAttributes( ConstantPool constantPool ) throws IOException, DecompileException {
        int size = this.readUnsignedShort();
        Map<String, Attribute> map = new HashMap<>();
        for ( int i = 0; i < size; i++ ) {
            String name = constantPool.getUtf8( readUnsignedShort() );
            int length = this.readInt();
            byte[] data = new byte[length];
            readFully( data );
            Attribute attribute = this.attributeFactory.create( name, constantPool );
            if ( attribute != null ) {
                ByteCodeReader reader = new ByteCodeReader( new ByteArrayInputStream( data ) );
                attribute.read( reader );
                map.put( name, attribute );
            }
        }
        return map;
    }

}
