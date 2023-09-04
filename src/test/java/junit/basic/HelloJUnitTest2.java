package junit.basic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloJUnitTest2 {
    @Test
    public void test1() {
        int a = Math.max(1, 2);
        assertEquals(2, a);
    }

    @Test
    public void test2() {
        int a = Math.max(3, 5);
        assertEquals(5, a);
    }
}
