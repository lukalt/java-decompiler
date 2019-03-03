package me.lukas81298.jdecompile.bytecode.cp;

import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.cp.item.*;

/**
 * @author lukas
 * @since 02.03.2019
 */
@RequiredArgsConstructor
public class ConstantPoolItemFactory {

    private final ConstantPool constantPool;

    /**
     * Creates new item implemenation by tag type
     * @see <a href="https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.4">https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.4</a>
     *
     * @param tag Type of the tag
     * @return Tag implementation according to tag
     */
    public ConstantPoolItem createItem( int tag ) throws DecompileException {
        switch ( tag ) {
            case 1:
                return new ConstantUtf8( this.constantPool );
            case 3:
                return new ConstantInteger( this.constantPool );
            case 4:
                return new ConstantFloat( this.constantPool );
            case 5:
                return new ConstantLong( this.constantPool );
            case 6:
                return new ConstantDouble( this.constantPool );
            case 7:
                return new ConstantClass( this.constantPool );
            case 8:
                return new ConstantString( this.constantPool );
            case 9:
                return new AbstractConstantRef.ConstantFieldRef( this.constantPool );
            case 10:
                return new AbstractConstantRef.ConstantMethodRef( this.constantPool );
            case 11:
                return new AbstractConstantRef.ConstantInterfaceMethodRef( this.constantPool );
            case 12:
                return new ConstantNameAndType( this.constantPool );

            case 15:
                return new ConstantMethodHandle( this.constantPool );
            case 16:
                return new ConstantMethodType( this.constantPool );
            case 17:
                return new ConstantInvokeDynamic( this.constantPool );

        }
        throw new DecompileException( "Cannot create item for illegal tag type: " + tag );
    }
}
