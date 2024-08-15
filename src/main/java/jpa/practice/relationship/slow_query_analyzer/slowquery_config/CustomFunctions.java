package jpa.practice.relationship.slow_query_analyzer.slowquery_config;

public class CustomFunctions {
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}