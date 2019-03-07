package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.cp.item.AbstractConstantRef;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class InvokeSpecialSpec extends InstructionSpec {

    public InvokeSpecialSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        AbstractConstantRef ref = context.getConstantPool().getItem( instruction.getUnsignedShort( 0 ) );
        String name = ref.getNameAndType().getName();
        String descriptor = ref.getNameAndType().getType();
        List<String> params = new ArrayList<>();
        writer.parseMethodDescriptor( descriptor, params );
        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < params.size(); i++ ) {
            if ( sb.length() > 0 ) {
                sb.append( ", " );
            }
            sb.append( writer.removeBrackets( Objects.toString( stack.pop().getValue() ) ) );
        }
        if ( ref.getClassInfo().getClassName().equals( context.getClassFile().getSuperClassName() ) ) {
            if ( name.equals( "<init>" ) ) {
                writer.writeln( level, "super(" + sb + ");" );
            } else {
                writer.writeln( level, "super." + name + "(" + sb + ");" );
            }
            stack.pop();
        } else {
            Operand operand = stack.pop();
            if ( name.equals( "<init>" ) && operand.getType() == OperandType.TYPE_REF ) {
                stack.push( new Operand( OperandType.REFERENCE, "new " + operand.getValue() + "(" + sb.toString() + ")" ) );
                stack.pop(); // we got one more type than we need on the stack, remove it to prevent further errors
            } else {
                stack.push( new Operand( OperandType.REFERENCE, operand.getValue() + "." + name + "(" + sb.toString() + ")" ) );
            }
        }

    }
}
