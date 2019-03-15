package me.lukas81298.jdecompile.bytecode.attribute;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class AttributeFactory {

    private final Map<String, Class<? extends Attribute>> registeredAttributes = new HashMap<>();

    public AttributeFactory() {
        this.registeredAttributes.put( "Code", CodeAttribute.class );
        this.registeredAttributes.put( "ConstantValue", ConstantValueAttribute.class );
        this.registeredAttributes.put( "Exceptions", ExceptionsAttribute.class );
        this.registeredAttributes.put( "Synthetic", SyntheticAttribute.class );
        this.registeredAttributes.put( "SourceFile", SourceFileAttribute.class );
        this.registeredAttributes.put( "LocalVariableTable", LocalVariableTableAttribute.class );
        this.registeredAttributes.put( "Deprecated", DeprecatedAttribute.class );
        this.registeredAttributes.put( "LineNumberTable", LineNumberTableAttribute.class );
        this.registeredAttributes.put( "RuntimeVisibleAnnotations", RuntimeVisibleAnnotationsAttribute.class );
    }

    public Attribute create( String name, ConstantPool constantPool ) throws DecompileException {
        Class<? extends Attribute> clazz = this.registeredAttributes.get( name );
        if ( clazz == null ) {
            return null; // unknown attribute, will be skipped
        }
        try {
            Constructor<? extends Attribute> constructor = clazz.getDeclaredConstructor( ConstantPool.class );
            return constructor.newInstance( constantPool );
        } catch ( Throwable t ) {
            t.printStackTrace();
            throw new DecompileException( "Cannot instantiate " + clazz.getSimpleName() );
        }
    }

}
