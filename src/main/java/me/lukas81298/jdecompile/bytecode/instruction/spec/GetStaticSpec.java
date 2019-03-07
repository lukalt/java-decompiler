package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.cp.item.AbstractConstantRef;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class GetStaticSpec extends InstructionSpec {

    public GetStaticSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        AbstractConstantRef.ConstantFieldRef fieldRef = context.getConstantPool().getItem( instruction.getUnsignedShort( 0 ) );
        stack.push( new Operand( OperandType.REFERENCE,
                writer.formatClassName( fieldRef.getClassInfo().getClassName() ) + "." + fieldRef.getNameAndType().getName() ) );
    }
}
