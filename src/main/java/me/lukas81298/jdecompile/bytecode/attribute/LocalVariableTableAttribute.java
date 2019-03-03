package me.lukas81298.jdecompile.bytecode.attribute;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class LocalVariableTableAttribute extends Attribute {

    private final TIntObjectMap<LocalVariable> localVar = new TIntObjectHashMap<>();

    public LocalVariableTableAttribute( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException, DecompileException {
        int len = reader.readUnsignedShort();
        for ( int i = 0; i < len; i++ ) {
            LocalVariable variable = new LocalVariable( reader.readUnsignedShort(), reader.readUnsignedShort(),
                    this.constantPool.getUtf8( reader.readUnsignedShort() ),
                    this.constantPool.getUtf8( reader.readUnsignedShort() ) );
            this.localVar.put( reader.readUnsignedShort(), variable );
        }
    }

    @RequiredArgsConstructor
    @Getter
    public final class LocalVariable {

        private final int startPc, length;
        private final String name;
        private final String descriptor;
    }
}
