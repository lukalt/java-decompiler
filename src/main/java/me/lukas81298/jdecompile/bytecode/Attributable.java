package me.lukas81298.jdecompile.bytecode;

import me.lukas81298.jdecompile.bytecode.attribute.Attribute;

import java.util.Map;

/**
 * @author lukas
 * @since 03.03.2019
 */
public interface Attributable {

    Map<String, Attribute> getAttributes();

    default boolean hasAttribute( String name ) {
        return this.getAttributes().containsKey( name );
    }

    @SuppressWarnings( "unchecked" )
    default <K extends Attribute> K getAttribute( String name ) {
        return (K) this.getAttributes().get( name );
    }

}
