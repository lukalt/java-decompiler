package me.lukas81298.jdecompile.bytecode.instruction.spec;

import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.cp.item.AbstractConstantRef;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantClass;
import me.lukas81298.jdecompile.bytecode.instruction.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author lukas
 * @since 06.03.2019
 */
public class InvokeVirtualSpec extends InstructionSpec {

    public InvokeVirtualSpec( String mnemonic, int typeId, int dataLen ) {
        super( mnemonic, typeId, dataLen );
    }

    @Override
    public void process( int level, Instruction instruction, Stack<Operand> stack, SourceCodeWriter writer, Context context ) throws DecompileException {
        AbstractConstantRef ref = context.getConstantPool().getItem( instruction.getUnsignedShort( 0 ) );
        String name = ref.getNameAndType().getName();
        String descriptor = ref.getNameAndType().getType();
        List<String> params = new ArrayList<>();
        String returnType = writer.parseMethodDescriptor( descriptor, params );
        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < params.size(); i++ ) {
            if ( sb.length() > 0 ) {
                sb.append( ", " );
            }
            sb.append( writer.removeBrackets( Objects.toString( stack.pop().getValue() ) ) );
        }
        this.handle( context, instruction, stack, ref.getClassInfo(), name, sb.toString(), writer, returnType.equals( "void" ), level );
    }

    protected void handle( Context context, Instruction instruction, Stack<Operand> stack, ConstantClass classInfo, String name, String params, SourceCodeWriter writer, boolean isVoid, int level ) throws DecompileException {
        if ( isVoid ) {
            writer.writeln( level, stack.pop().getValue() + "." + name + "(" + params + ");" );
        } else {
            stack.push( new Operand( OperandType.REFERENCE, stack.pop().getValue() + "." + name + "(" + params + ")" ) );
        }
    }

}
