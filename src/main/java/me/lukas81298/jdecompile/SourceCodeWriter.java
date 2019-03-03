package me.lukas81298.jdecompile;

import lombok.Getter;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author lukas
 * @since 02.03.2019
 */
public class SourceCodeWriter implements Closeable {

    private final Set<String> AUTO_IMPORTED_PACKAGES = new HashSet<>( Collections.singletonList( "java.lang" ) );

    private final PrintWriter writer;
    @Getter
    private final IndentMode indentMode;

    @Getter
    private final Set<String> importedClasses = new HashSet<>();
    private final Set<String> importedSimpleNames = new HashSet<>();
    public SourceCodeWriter( OutputStream outputStream, IndentMode indentMode ) {
        this.writer = new PrintWriter( outputStream );
        this.indentMode = indentMode;
    }

    public void close() {
        this.writer.close();
    }

    public void writeln( int level ) {
        this.writer.println( this.indentMode.indent( level ) );
    }

    public void writeln( int level, String s ) {
        this.writer.println( this.indentMode.indent( level ) + s );
    }

    public String parseType( String input ) throws DecompileException {
        if ( input.isEmpty() ) {
            throw new DecompileException( "Cannot parse empty type" );
        }
        return this.parseType( new StringQueue( input ) );
    }

    public String parseMethodDescriptor( String input, List<String> parameters ) throws DecompileException {
        return this.parseMethodDescriptor( new StringQueue( input ), parameters );
    }

    private String parseMethodDescriptor( StringQueue input, List<String> parameters ) throws DecompileException {
        if ( input.pop() != '(' ) {
            throw new DecompileException( input + " does not start with a '('" );
        }
        while ( input.peek() != ')' ) {
            parameters.add( parseType( input ) );
        }
        input.pop(); // remove closing bracket
        return parseType( input );
    }


    private String parseType( StringQueue queue ) throws DecompileException {
        char first = queue.pop();
        switch ( first ) {
            case 'V':
                return "void";
            case 'B':
                return "byte";
            case 'C':
                return "char";
            case 'D':
                return "double";
            case 'F':
                return "float";
            case 'I':
                return "int";
            case 'J':
                return "long";
            case 'S':
                return "short";
            case 'Z':
                return "boolean";
            case '[':
                return this.parseType( queue ) + "[]"; // thats an array, lookup array type recursively
            case 'L':
                int c = queue.indexOf( ';' );
                if ( c == -1 ) {
                    throw new DecompileException( "Missing termination: " + queue.remainingString() );
                }
                String s = queue.pop( c );
                queue.pop();  // remove tailing semicolon
                return formatClassName( s );
            default:
                throw new DecompileException( "Invalid type indicator: " + first );
        }
    }

    public String formatClassName( String name ) {
        name = name.replace( "/", "." );
        int li = name.lastIndexOf( "." );
        if ( li >= 0 ) {
            String pack = name.substring( 0, li );
            String clazz = name.substring( li + 1 );
            if ( AUTO_IMPORTED_PACKAGES.contains( pack ) ) {
                return clazz;
            }
            if ( this.importedClasses.contains( name ) ) {
                return clazz;
            }
            if( !this.importedSimpleNames.add( clazz ) ) {
                return name;
            }
            this.importedClasses.add( name );
            return clazz;
        }
        return name;
    }
}
