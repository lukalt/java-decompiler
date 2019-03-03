package me.lukas81298.jdecompile.bytecode.instruction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

}
