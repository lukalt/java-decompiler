/**
 * @author lukas
 * @since 03.03.2019
 */
public class Test {

    private static int test = 0;

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

    public static void main(String[] args) {
        test++;
    }

    private int neg( int x ) {
        return -1 * x;
    }

    public int g() {
        int a = test;
        test = a + 1;
        return a;
    }
}
