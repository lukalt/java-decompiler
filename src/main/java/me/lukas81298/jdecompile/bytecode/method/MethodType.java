package me.lukas81298.jdecompile.bytecode.method;

/**
 * @author lukas
 * @since 03.03.2019
 */
public enum MethodType {

    CLASS_CONSTRUCTOR,
    CONSTRUCTOR,
    METHOD;

    public static MethodType byMethodName( String name ) {
        switch ( name ) {
            case "<init>":
                return CONSTRUCTOR;
            case "<clinit>":
                return CLASS_CONSTRUCTOR;
            default:
                return METHOD;
        }
    }
}
