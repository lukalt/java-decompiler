package me.lukas81298.jdecompile.bytecode.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.lukas81298.jdecompile.CodeWriteable;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.AccessFlag;
import me.lukas81298.jdecompile.bytecode.Attributable;
import me.lukas81298.jdecompile.bytecode.ClassFile;
import me.lukas81298.jdecompile.bytecode.attribute.Attribute;
import me.lukas81298.jdecompile.bytecode.attribute.ConstantValueAttribute;

import java.util.Map;
import java.util.Set;

/**
 * @author lukas
 * @since 02.03.2019
 */
@Getter
@AllArgsConstructor
public class FieldInfo implements CodeWriteable, Attributable {

    private final Set<AccessFlag> flags;
    private final String name;
    private final String descriptor;
    private final Map<String, Attribute> attributes;
    private final ClassFile classFile;

    private String getSignature( SourceCodeWriter sourceCodeWriter ) throws DecompileException {
        StringBuilder buf = new StringBuilder();
        String mod = this.formatAccessModifier();
        if ( !mod.isEmpty() ) {
            buf.append( mod ).append( " " );
        }
        if ( this.flags.contains( AccessFlag.ACC_STATIC ) ) {
            buf.append( "static " );
        }
        if ( this.flags.contains( AccessFlag.ACC_FINAL ) ) {
            buf.append( "final " );
        }
        if ( this.flags.contains( AccessFlag.ACC_TRANSIENT ) ) {
            buf.append( "transient " );
        }
        if ( this.flags.contains( AccessFlag.ACC_VOLATILE ) ) {
            buf.append( "volatile " );
        }
        buf.append( sourceCodeWriter.parseType( descriptor ) ).append( " " ).append( name );
        if ( this.hasAttribute( "ConstantValue" ) ) {
            ConstantValueAttribute constantValueAttribute = this.getAttribute( "ConstantValue" );
            buf.append( " = " ).append( constantValueAttribute.getValue() );
        }
        buf.append( ";" );
        return buf.toString();
    }

    public boolean isSynthetic() {
        return this.flags.contains( AccessFlag.ACC_SYNTHETIC ) || this.hasAttribute( "Synthetic" );
    }

    @Override
    public void write( SourceCodeWriter writer, int level ) throws DecompileException {
        writer.writeln( level, this.getSignature( writer ) );
    }
}
