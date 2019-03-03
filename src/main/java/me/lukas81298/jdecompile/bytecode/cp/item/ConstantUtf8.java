package me.lukas81298.jdecompile.bytecode.cp.item;

import lombok.Getter;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPoolItem;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 02.03.2019
 */
@Getter
public class ConstantUtf8 extends ConstantPoolItem {

    private String content;

    public ConstantUtf8( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException {
        this.content = reader.readUTF();
    }

    @Override
    public String asString() {
        return this.content;
    }

}
