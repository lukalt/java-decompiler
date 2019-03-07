package me.lukas81298.jdecompile.bytecode.instruction.spec;

/**
 * @author lukas
 * @since 07.03.2019
 */
public class InvokeInterfaceSpec extends InvokeVirtualSpec {

    public InvokeInterfaceSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

}
