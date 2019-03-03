package me.lukas81298.jdecompile;

/**
 * @author lukas
 * @since 03.03.2019
 */
public class StringQueue {

    private final char[] chars;
    private final String asString;

    private int tail = 0;

    public StringQueue( String input ) {
        this.asString = input;
        this.chars = input.toCharArray();
    }

    public boolean hasNext() {
        return this.tail < this.chars.length;
    }

    public int remaining() {
        return this.chars.length - this.tail;
    }

    public char peek() {
        if( !this.hasNext() ) {
            throw new IllegalStateException( "buffer is already empty" );
        }
        return this.chars[this.tail];
    }

    public char pop() {
        char c = this.peek();
        this.tail++;
        return c;
    }

    public String peek( int len ) {
        return this.asString.substring( this.tail, this.tail + len );
    }

    public String pop( int len ) {
        String s = this.asString.substring( this.tail, this.tail + len );
        this.tail += len;
        return s;
    }

    public int indexOf( char c ) {
        return this.asString.indexOf( c, this.tail ) - this.tail;
    }

    public String remainingString() {
        return this.asString.substring( this.tail );
    }
}
