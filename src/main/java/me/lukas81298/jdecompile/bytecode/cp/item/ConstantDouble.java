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
public class ConstantDouble extends ConstantPoolItem {

    private double value;

    public ConstantDouble( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException {
        this.value = reader.readDouble();
    }

    @Override
    public String asString() {
        return value + "D";
    }

    @Override
    public byte getSize() {
        return 2;
    }

}
