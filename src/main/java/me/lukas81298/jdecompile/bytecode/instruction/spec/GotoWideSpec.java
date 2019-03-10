package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.bytecode.instruction.Instruction;

/**
 * @author lukas
 * @since 07.03.2019
 */
public final class GotoWideSpec extends AbstractGotoSpec {

    public GotoWideSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public int getBranch( Instruction instruction ) {
        return instruction.getPc() + instruction.getUnsignedInt( 0 );
    }
}