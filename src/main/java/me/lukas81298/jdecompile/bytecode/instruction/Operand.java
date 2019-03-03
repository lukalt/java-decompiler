package me.lukas81298.jdecompile.bytecode.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lukas
 * @since 03.03.2019
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Operand {

    private final OperandType type;
    private Object value = null;
}
