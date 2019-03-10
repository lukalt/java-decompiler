/**
 * @author lukas
 * @since 03.03.2019
 */
public class Test {

    /*private static int test = 0;

    float p;
    int j;

    String h = "TEST";

    public int f() {
        int a = 0;
        byte b = 23;
        a = b;
        int c = a + b;
        int d = b + a;
        j = c + d;
        p = 3F;
        j = (int) p;
        h = h + "_";
        neg( j );
        main( null );
        return neg( j );
    }

    public static void main( String[] args ) {
        test++;
    }

    private int neg( int x ) {
        return -1 * x;
    }

    public int g() {
        int a = test;
        test = a + 1;
        return a;
    }*/

    public static final int CONST_VALUE = 10;

    public int par3( int x, int y, int z ) {
        x = y + z;
        int v = x * y;
        v = (int) Math.sqrt( v );
        return v;
    }

    private void j( int x ) {
        if ( x % 2 == 0 ) {
            System.out.println( "GERADE" );
        } else {
            System.out.println( "UNGERADE" );
        }
    }

    private int m(int x, int y) {
        while ( x > 0 && x != y ) {
            if(x % 2 == 0) {
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
