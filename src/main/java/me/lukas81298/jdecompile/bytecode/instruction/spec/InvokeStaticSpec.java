package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.SourceCodeWriter;
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
    protected void handle( Stack<Operand> stack, String className, String name, String params, SourceCodeWriter writer, boolean isVoid, int level ) {
        if( isVoid ) {
            writer.writeln( level, className + "." + name + "(" + params + ");" );
        } else {
            stack.push( new Operand( OperandType.REFERENCE, className + "." + name + "(" + params + ")" ) );
        }
    }
}
