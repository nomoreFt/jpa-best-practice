package jpa.practice.relationship.sqlcount_assert.config;

public class CustomFunctions {
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}