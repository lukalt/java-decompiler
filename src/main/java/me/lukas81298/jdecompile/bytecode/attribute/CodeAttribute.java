package me.lukas81298.jdecompile.bytecode.attribute;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.DecompileException;
import me.lukas81298.jdecompile.bytecode.Attributable;
import me.lukas81298.jdecompile.bytecode.cp.ConstantPool;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;
import me.lukas81298.jdecompile.bytecode.instruction.InstructionSpec;
import me.lukas81298.jdecompile.bytecode.instruction.InstructionTable;
import me.lukas81298.jdecompile.bytecode.io.ByteCodeReader;

import java.io.IOException;
import java.util.Map;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class CodeAttribute extends Attribute implements Attributable {

    private ExceptionItem[] exceptions;

    @Getter
    private TIntObjectMap<Instruction> instructions = new TIntObjectHashMap<>();

    @Getter
    private Map<String, Attribute> attributes;

    public CodeAttribute( ConstantPool constantPool ) {
        super( constantPool );
    }

    @Override
    public void read( ByteCodeReader reader ) throws IOException, DecompileException {
        reader.readUnsignedShort();
        reader.readUnsignedShort();
        // read two u2, not needed for decompilation

        boolean isWide = false;
        int pc = 0;
        int toRead = reader.readInt();

        InstructionTable instructionTable = InstructionTable.getInstance();

        while ( toRead > 0 ) {
            int type = reader.readUnsignedByte();
            InstructionSpec spec = instructionTable.getSpec( type );
            int[] addData = new int[]{ spec.getDataLen() };

            for ( int i = 0; i < addData.length; i++ ) {
                if ( isWide ) { // wide means 2 byte, otherwise 1
                    addData[i] = reader.readUnsignedShort();
                } else {
                    addData[i] = reader.readUnsignedByte();
                }
            }

            toRead -= ( 1 + (isWide ? 2 : 1) * spec.getDataLen() ); // decrement the bytes read
            this.instructions.put( pc, new Instruction( pc, spec, addData ) );
            pc = pc + 1 + spec.getDataLen();
            isWide = type == 0xC4; // interpret the next statement as wide if type is 0xC4 (mnemonic wide)
        }

        this.exceptions = new ExceptionItem[reader.readUnsignedShort()];
        for ( int i = 0; i < this.exceptions.length; i++ ) {
            this.exceptions[i] = new ExceptionItem( reader.readUnsignedShort(), reader.readUnsignedShort(), reader.readUnsignedShort(), reader.readUnsignedShort() );
        }
        this.attributes = reader.readAttributes( this.constantPool );
    }

    @RequiredArgsConstructor
    @Getter
    private final class ExceptionItem {

        private final int startPc, endPc, handlePc, catchType;
    }
}
