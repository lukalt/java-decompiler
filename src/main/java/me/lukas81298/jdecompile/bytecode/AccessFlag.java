package me.lukas81298.jdecompile.bytecode;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * @author lukas
 * @since 02.03.2019
 */
@RequiredArgsConstructor
public enum AccessFlag {

    ACC_PUBLIC( 0x0001 ),
    ACC_PRIVATE( 0x0002 ),
    ACC_PROTECTED( 0x0004 ),
    ACC_STATIC( 0x0008 ),
    ACC_FINAL( 0x0010 ),
    ACC_SUPER( 0x0020 ),
    ACC_VOLATILE( 0x0040 ),
    ACC_VARARGS( 0x0080 ),
    ACC_NATIVE( 0x0100 ),
    ACC_INTERFACE( 0x0200 ),
    ACC_ABSTRACT( 0x0400 ),
    ACC_TRANSIENT( 0x0800 ),
    ACC_SYNTHETIC( 0x1000 ),
    ACC_ANNOTATION( 0x2000 ),
    ACC_ENUM( 0x4000 );

    @Getter
    private final int flag;

    public static <K extends Collection<AccessFlag>> K fromBitmask( int flags, @NonNull K collection ) {
        for ( AccessFlag value : AccessFlag.values() ) {
            if ( ( value.flag & flags ) > 0 ) {
                collection.add( value );
            }
        }
        return collection;
    }
}
