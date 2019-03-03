package me.lukas81298.jdecompile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lukas
 * @since 02.03.2019
 */
@RequiredArgsConstructor
@Getter
public class DecompileException extends Exception {

    private final String message;

}
