package me.lukas81298.jdecompile.bytecode.instruction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lukas
 * @since 03.03.2019
 */
@RequiredArgsConstructor
@Getter
public enum OperandType {

    INT( "int" ),
    LONG( "long" ),
    FLOAT( "float" ),
    DOUBLE( "double" ),
    CHAR( "char" ),
    BYTE( "byte" ),
    REFERENCE( "Object" ),
    TYPE_REF( "Object" ),
    SHORT( "short" ),
    BOOLEAN( "boolean" );

    private final String type;

}
