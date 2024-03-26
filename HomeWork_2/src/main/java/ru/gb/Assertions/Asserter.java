package ru.gb.Assertions;

public class Asserter {

    public static void assertEquals(int expected, int actual) throws AssertionMessage {
        if (expected == actual) {
            throw new AssertionMessage("OK");
        } else {
            throw new AssertionMessage(
                    String.format("FAIL - expected %d, actual %d", expected, actual));
        }
    }
}
