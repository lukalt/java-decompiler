package me.lukas81298.jdecompile.bytecode.method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.lukas81298.jdecompile.CodeWriteable;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.AccessFlag;
import me.lukas81298.jdecompile.bytecode.Attributable;
import me.lukas81298.jdecompile.bytecode.ClassFile;
import me.lukas81298.jdecompile.bytecode.attribute.Attribute;
import me.lukas81298.jdecompile.bytecode.attribute.CodeAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lukas
 * @since 02.03.2019
 */
@Getter
public class MethodInfo implements CodeWriteable, Attributable {

    private final Set<AccessFlag> flags;
    private final String name;
    private final String descriptor;
    private final Map<String, Attribute> attributes;
    private final ClassFile classFile;
    private final MethodType type;

    public MethodInfo( Set<AccessFlag> flags, String name, String descriptor, Map<String, Attribute> attributes, ClassFile classFile ) {
        this.flags = flags;
        this.name = name;
        this.descriptor = descriptor;
        this.attributes = attributes;
        this.classFile = classFile;
        this.type = MethodType.byMethodName( this.name );
    }

    private String getSignature( SourceCodeWriter writer ) throws DecompileException {
        if( this.type == MethodType.CLASS_CONSTRUCTOR ) {
            return "static {";
        }
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
        if ( this.flags.contains( AccessFlag.ACC_ABSTRACT ) ) {
            buf.append( "abstract " );
        }
        List<String> params = new ArrayList<>();
        String returnType = writer.parseMethodDescriptor( this.descriptor, params );
        if( this.type == MethodType.METHOD ) {
            buf.append( returnType ).append( " " );
            buf.append( name );
        } else {
            buf.append( writer.formatClassName( classFile.getClassName() ) );
        }
        buf.append( '(' );
        int j = 0;
        for ( String param : params ) {
            if( j > 0 ) {
                buf.append( ", " );
            }
            buf.append( param ).append( " p" ).append( j );
            j++;
        }
        buf.append( ')' );
        return buf.toString();
    }

    public boolean isSynthetic() {
        return this.flags.contains( AccessFlag.ACC_SYNTHETIC ) || this.hasAttribute( "Synthetic" );
    }

    @Override
    public void write( SourceCodeWriter writer, int level ) throws DecompileException {
        writer.writeln( level, getSignature( writer ) + " {" );
        if( this.hasAttribute( "Code" ) ) {
            CodeAttribute attr = this.getAttribute( "Code" );

        }
        writer.writeln( level, "}" );
        writer.writeln( level );
    }

}
