package me.lukas81298.jdecompile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lukas
 * @since 03.03.2019
 */
@Getter
@RequiredArgsConstructor
public class BinTuple<A,B> {

    private final A a;
    private final B b;
}
