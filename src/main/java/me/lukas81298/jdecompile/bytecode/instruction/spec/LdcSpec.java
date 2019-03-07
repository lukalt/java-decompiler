package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPoolItem;
import me.lukas81298.jdecompile.bytecode.cp.item.*;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.Stack;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class LdcSpec extends InstructionSpec {

    public LdcSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        ConstantPoolItem item = context.getConstantPool().getItem( this.getIndex( instruction ) );
        if ( item instanceof ConstantFloat ) {
            stack.push( new Operand( OperandType.FLOAT, ( (ConstantFloat) item ).getValue() ) );
        } else if ( item instanceof ConstantInteger ) {
            stack.push( new Operand( OperandType.INT, ( (ConstantInteger) item ).getValue() ) );
        } else if ( item instanceof ConstantDouble ) {
            stack.push( new Operand( OperandType.DOUBLE, ( (ConstantDouble) item ).getValue() ) );
        } else if ( item instanceof ConstantLong ) {
            stack.push( new Operand( OperandType.LONG, ( (ConstantLong) item ).getValue() ) );
        } else if ( item instanceof ConstantString ) {
            stack.push( new Operand( OperandType.REFERENCE, "\"" + ( (ConstantString) item ).getValue() + "\"" ) );
        } else {
            stack.push( new Operand( OperandType.REFERENCE, item.asString() ) );
        }
    }

    protected int getIndex( Instruction instruction ) throws DecompileException {
        return instruction.getData()[0];
    }


}
