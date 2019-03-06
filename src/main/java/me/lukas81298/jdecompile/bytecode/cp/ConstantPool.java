package me.lukas81298.jdecompile.bytecode.cp;

import lombok.ToString;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantString;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantUtf8;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;

/**
 * @author lukas
 * @since 02.03.2019
 */
@ToString( of = "items" )
public class ConstantPool {

    private ConstantPoolItem[] items;
    private final ConstantPoolItemFactory itemFactory;

    public ConstantPool() {
        this.itemFactory = new ConstantPoolItemFactory( this );
    }

    @SuppressWarnings( "unchecked" )
    public <K extends ConstantPoolItem> K getItem( int index ) throws DecompileException {
        ConstantPoolItem item = this.items[index - 1];
        if ( item == null ) {
            throw new DecompileException( "Index " + index + " not present in constant pool. " + this.items.length );
        }
        return (K) item;
    }

    public String getUtf8( int index ) throws DecompileException {
        return ( (ConstantUtf8) this.getItem( index ) ).getContent();
    }

    public String getString( int index ) throws DecompileException {
        return ( (ConstantString) this.getItem( index ) ).getValue();
    }

    public void read( ByteCodeReader reader ) throws DecompileException, IOException {
        final int count = reader.readUnsignedShort(); // read number of items to read
        this.items = new ConstantPoolItem[count - 1]; // constant pool is indexed from 1 to size - 1, substract 1 at every access to make use of the 0. slot
        int i = 1;
        while ( i < count ) {
            final int type = reader.readUnsignedByte(); // read the type of the item to read
            final ConstantPoolItem item = this.itemFactory.createItem( type );
            item.read( reader );
            for ( byte j = 0; j < item.getSize(); j++ ) { // doubles and longs take two slots, put them in both
                this.items[i + j - 1] = item;
                i++;
            }
        }
        System.out.println( this.items.length );
    }


}
