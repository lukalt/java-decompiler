package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantClass;
import me.lukas81298.jdecompile.bytecode.instruction.Context;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.Operand;
import me.lukas81298.jdecompile.bytecode.instruction.OperandType;

import java.util.Stack;

/**
 * @author lukas
 * @since 07.03.2019
 */
public class InvokeStaticSpec extends InvokeVirtualSpec {

    public InvokeStaticSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    protected void handle( Context context, Instruction instruction, Stack<Operand> stack, ConstantClass constantClass, String name, String params, SourceCodeWriter writer, boolean isVoid, int level ) throws DecompileException  {
        if( isVoid ) {
            writer.writeln( level, context.getLineNumber( instruction ),writer.formatClassName( constantClass.getClassName() ) + "." + name + "(" + params + ");" );
        } else {
            stack.push( new Operand( OperandType.REFERENCE, writer.formatClassName( constantClass.getClassName() ) + "." + name + "(" + params + ")" ) );
        }
    }
}
