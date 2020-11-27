package test.print;

import org.junit.Test;

public class TestPrint {

    @Test
    public void test() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
        }
    }
}
