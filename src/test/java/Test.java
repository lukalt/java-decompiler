/**
 * @author lukas
 * @since 03.03.2019
 */
public class Test {

    public static final int CONST_VALUE = 10;

    public int par3( int x, int y, int z ) {
        x = y + z;
        int v = x * y;
        v = (int) Math.sqrt( v );
        return v;
    }

    void test() {
        System.out.println( "A" + "B" + "C" + "D" );
        String nicht = "NICHT";
        String toll = "TOLL";
        System.out.println( nicht + toll );
    }

    private void j( int x ) {
        if ( x % 2 == 0 ) {
            System.out.println( "GERADE" );
        } else {
            System.out.println( "UNGERADE" );

        }

    }

    private int m( int x, int y ) {
        while ( x > 0 && x != y ) {
            if ( x % 2 == 0 ) {
                x--;
            } else {
                y--;
            }
        }
        return x;
    }

    private void k( int x ) {
        if ( x % 3 == 0 ) {
            System.out.println( "D" );
        }
        System.out.println( "X" );
    }

    public int fac( int input ) {
        if ( input < 0 ) {
            return 0;
        }
        int value = 1;
        while ( input > 0 ) {
            value *= input;
            input--;
            for ( int i = 0; i < 10; i++ ) {
                input++;
            }
        }
        return value;
    }
}
