package me.lukas81298.jdecompile;

import me.lukas81298.jdecompile.bytecode.AccessFlag;

import java.util.Set;

/**
 * @author lukas
 * @since 03.03.2019
 */
public interface CodeWriteable {

    void write( SourceCodeWriter writer, int level ) throws DecompileException;

    Set<AccessFlag> getFlags();

    default String formatAccessModifier() {
        if ( this.getFlags().contains( AccessFlag.ACC_PUBLIC ) ) {
            return "public";
        }
        if ( this.getFlags().contains( AccessFlag.ACC_PRIVATE ) ) {
            return "private";
        }
        if ( this.getFlags().contains( AccessFlag.ACC_PROTECTED ) ) {
            return "protected";
        }
        return "";
    }
}
