package me.lukas81298.jdecompile.bytecode.flow;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lukas81298.jdecompile.bytecode.instruction.Instruction;

import java.util.List;

/**
 * @author lukas
 * @since 10.03.2019
 */
@Getter
@RequiredArgsConstructor
public class InstructionEntry {

    private final List<Instruction> instructions;
    private final Instruction condition;

}
