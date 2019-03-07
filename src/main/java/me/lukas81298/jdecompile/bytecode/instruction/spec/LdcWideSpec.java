package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class LdcWideSpec extends LdcSpec {

    public LdcWideSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    protected int getIndex( Instruction instruction ) throws DecompileException {
        return instruction.getUnsignedShort( 0 );
    }
}
