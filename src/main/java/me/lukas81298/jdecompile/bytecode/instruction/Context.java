package me.lukas81298.jdecompile.bytecode.instruction;

import gnu.trove.map.TIntObjectMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.bytecode.attribute.LocalVariableTableAttribute;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;

/**
 * @author lukas
 * @since 03.03.2019
 */
@Getter
@RequiredArgsConstructor
public class Context {

    private final ConstantPool constantPool;
    private final TIntObjectMap<LocalVariableTableAttribute.LocalVariable> variables;

    public String getLocalVariable( int id ) {
        LocalVariableTableAttribute.LocalVariable variable = this.variables.get( id );
        if( variable == null ) {
            return "local_" + id;
        }
        return variable.getName();
    }
}
