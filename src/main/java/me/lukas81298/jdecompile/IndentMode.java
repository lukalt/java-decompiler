package me.lukas81298.jdecompile;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author lukas
 * @since 02.03.2019
 */
@Getter
@RequiredArgsConstructor
public enum IndentMode {
    TAB( "\u0009" ),
    SPACE_2( "  " ),
    SPACE_4( "    " );
    private final String flag;

    public String indent( int level ) {
        if ( level == 0 ) {
            return "";
        }
        return Strings.repeat( this.flag, level );
    }
}
