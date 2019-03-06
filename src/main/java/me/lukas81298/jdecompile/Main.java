package me.lukas81298.jdecompile;

import me.lukas81298.jdecompile.bytecode.ClassFile;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class Main {

    public static void main( String[] args ) {
        try {
            File file = new File( "C://Users\\lukas\\Desktop\\Test.class" );
            FileInputStream in = new FileInputStream( file );
            ClassFile classFile = new ClassFile();
            classFile.read( new ByteCodeReader( in ) );

            try( final SourceCodeWriter writer = new SourceCodeWriter( System.out, IndentMode.SPACE_2 ) ) {
                classFile.write( writer, 0 );
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

}
