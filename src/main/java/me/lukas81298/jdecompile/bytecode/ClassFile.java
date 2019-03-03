package me.lukas81298.jdecompile.bytecode;

import lombok.Getter;
import lombok.ToString;
import me.lukas81298.jdecompile.CodeWriteable;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.SourceCodeWriter;
import me.lukas81298.jdecompile.bytecode.attribute.Attribute;
import me.lukas81298.jdecompile.bytecode.attribute.SourceFileAttribute;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.cp.item.ConstantClass;
import me.lukas81298.jdecompile.bytecode.field.FieldInfo;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;
import me.lukas81298.jdecompile.bytecode.method.MethodInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author lukas
 * @since 02.03.2019
 */
@ToString
public class ClassFile implements CodeWriteable, Attributable {

    private ConstantPool constantPool;

    private int minorVersion;
    private int majorVersion;

    @Getter
    private final Set<AccessFlag> flags = new HashSet<>();

    @Getter
    private String className;
    private String packageName;
    private String superClassName;

    private FieldInfo[] fields;

    private String[] interfaces;

    private MethodInfo[] methods;

    @Getter
    private Map<String, Attribute> attributes;

    public void read( ByteCodeReader reader ) throws DecompileException, IOException {

        int magicNumber = reader.readInt();
        if ( magicNumber != 0xCAFEBABE ) {
            throw new DecompileException( "Unexpected magic number 0x" + Long.toHexString( magicNumber ) + ", expected: 0xCAFEBABE. Is this a class file?" );
        }

        this.minorVersion = reader.readUnsignedShort();
        this.majorVersion = reader.readUnsignedShort();

        this.constantPool = new ConstantPool();
        this.constantPool.read( reader );

        AccessFlag.fromBitmask( reader.readUnsignedShort(), this.flags );

        String fqClassName = ( (ConstantClass) this.constantPool.getItem( reader.readUnsignedShort() ) ).getClassName();
        int ind = fqClassName.lastIndexOf( "/" );
        if ( ind < 0 ) {
            this.packageName = "";
            this.className = fqClassName;
        } else {
            this.packageName = fqClassName.substring( 0, ind ).replace( "/", "." );
            this.className = fqClassName.substring( 1 + ind );
        }
        this.superClassName = ( (ConstantClass) this.constantPool.getItem( reader.readUnsignedShort() ) ).getClassName();

        int interfaces = reader.readUnsignedShort();
        this.interfaces = new String[interfaces];
        for ( int i = 0; i < interfaces; i++ ) {
            this.interfaces[i] = ( (ConstantClass) constantPool.getItem( reader.readUnsignedShort() ) ).getClassName();
        }
        this.fields = new FieldInfo[reader.readUnsignedShort()];
        for ( int i = 0; i < this.fields.length; i++ ) {
            this.fields[i] = reader.readFieldInfo( this.constantPool, this );
        }
        this.methods = new MethodInfo[reader.readUnsignedShort()];
        for ( int i = 0; i < this.methods.length; i++ ) {
            this.methods[i] = reader.readMethodInfo( this.constantPool, this );
        }
        this.attributes = reader.readAttributes( constantPool );
    }

    private boolean hasFlag( AccessFlag accessFlag ) {
        return this.flags.contains( accessFlag );
    }

    private String getSignature( SourceCodeWriter sourceCodeWriter ) {
        StringBuilder buf = new StringBuilder();
        String mod = this.formatAccessModifier();
        if ( !mod.isEmpty() ) {
            buf.append( mod ).append( " " );
        }
        if ( this.hasFlag( AccessFlag.ACC_STATIC ) ) {
            buf.append( "static " );
        }
        if ( this.hasFlag( AccessFlag.ACC_FINAL ) ) {
            buf.append( "final " );
        }
        if ( this.hasFlag( AccessFlag.ACC_ENUM ) ) {
            buf.append( "enum " );
        } else {
            buf.append( "class " );
        }
        buf.append( this.className ).append( " " );
        String sup = sourceCodeWriter.formatClassName( this.superClassName );
        if ( !sup.equals( "Object" ) ) {
            buf.append( "extends " ).append( sup ).append( " " );
        }
        if ( this.interfaces.length > 0 ) {
            buf.append( "implements " );
            for ( int i = 0; i < this.interfaces.length; i++ ) {
                if ( i > 0 ) {
                    buf.append( ", " );
                }
                buf.append( sourceCodeWriter.formatClassName( this.interfaces[i] ) );
            }
        }
        buf.append( " {" );
        return buf.toString();
    }

    @Override
    public void write( SourceCodeWriter writer, int level ) throws DecompileException {
        if( this.hasAttribute( "SourceFile" ) ) {
            SourceFileAttribute attribute = this.getAttribute( "SourceFile" );
            writer.writeln( level, "/*" );
            writer.writeln( level, " * Decompiled from: " + attribute.getFileName() );
            writer.writeln( level, "*/" );
        }
        if ( !this.packageName.isEmpty() ) {
            writer.writeln( level, "package " + this.packageName + ";" );
            writer.writeln( level );
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try ( SourceCodeWriter subWriter = new SourceCodeWriter( bos, writer.getIndentMode() ) ) {
            subWriter.writeln( level, this.getSignature( writer ) );
            subWriter.writeln( level + 1 );
            boolean w = false;
            for ( FieldInfo field : this.fields ) {
                if( field.isSynthetic() ) {
                    continue;
                }
                field.write( subWriter, level + 1 );
                w = true;
            }
            if ( w ) {
                subWriter.writeln( level + 1 );
            }
            w = false;
            for ( MethodInfo method : this.methods ) {
                if( method.isSynthetic() ) {
                    continue;
                }
                method.write( subWriter, level + 1 );
                w = true;
            }
            if ( w ) {
                subWriter.writeln( level + 1 );
            }
            subWriter.writeln( level, "}" );

            List<String> imports = new ArrayList<>( subWriter.getImportedClasses() );
            imports.sort( String::compareToIgnoreCase );
            String last = null;
            for ( String anImport : imports ) {
                String s = anImport.split( "\\." )[0];
                if ( last != null && !s.equals( last ) ) {
                    writer.writeln( level );
                }
                last = s;
                writer.writeln( level, "import " + anImport + ";" );
            }
            if ( !imports.isEmpty() ) {
                writer.writeln( level );
            }
        }
        writer.writeln( level, new String( bos.toByteArray() ) );
    }

}
