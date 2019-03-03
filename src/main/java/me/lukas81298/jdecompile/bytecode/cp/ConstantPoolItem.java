package me.lukas81298.jdecompile.bytecode.cp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 02.03.2019
 */
@Getter
@RequiredArgsConstructor
public abstract class ConstantPoolItem {

    protected final ConstantPool constantPool;

    public abstract void read( ByteCodeReader reader ) throws DecompileException, IOException;

    public abstract String asString() throws DecompileException;

    /**
     * The size of the current tag. Everything except longs and doubles has a size of 1.
     * @return tag size
     */
    public byte getSize() {
        return 1;
    }

    public String toString() {
        try {
            return this.asString();
        } catch ( DecompileException e ) {
            throw new RuntimeException( e );
        }
    }
}
