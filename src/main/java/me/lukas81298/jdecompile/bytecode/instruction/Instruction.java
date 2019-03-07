package me.lukas81298.jdecompile.bytecode.instruction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author lukas
 * @since 03.03.2019
 */
@RequiredArgsConstructor
@Getter
public class Instruction {

    private final int pc;
    private final InstructionSpec instructionSpec;
    private final int[] data;
    @Setter
    private boolean last;

    public int getUnsignedShort( int index ) {
        return (this.data[index] << 8) | this.data[index + 1];
    }

}
