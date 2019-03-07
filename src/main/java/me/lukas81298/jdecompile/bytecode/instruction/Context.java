package me.lukas81298.jdecompile.bytecode.instruction;

import gnu.trove.map.TIntObjectMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.ClassFile;
import me.lukas81298.jdecompile.bytecode.attribute.LocalVariableTableAttribute;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lukas
 * @since 03.03.2019
 */
@Getter
@RequiredArgsConstructor
public class Context {

    private final ConstantPool constantPool;
    private final TIntObjectMap<LocalVariableTableAttribute.LocalVariable> variables;
    private final Set<String> scopeDefinedVars = new HashSet<>();
    private final ClassFile classFile;

    public String getLocalVariableType( int id, SourceCodeWriter writer ) throws DecompileException {
        LocalVariableTableAttribute.LocalVariable variable = this.variables.get( id );
        if( variable == null ) {
            return "Object";
        }
        return writer.parseType( variable.getDescriptor() );
    }

    public String getLocalVariable( int id ) {
        LocalVariableTableAttribute.LocalVariable variable = this.variables.get( id );
        if( variable == null ) {
            return "local_" + id;
        }
        return variable.getName();
    }
}
