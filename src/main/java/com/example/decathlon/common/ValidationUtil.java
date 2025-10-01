package com.example.decathlon.common;

public class ValidationUtil {

    public static void validateResult(String disciplineName, double result, double min, double max) {
        if (result < min || result > max) {
            throw new IllegalArgumentException(
                    "Invalid value for " + disciplineName + ": " + result +
                            " (must be between " + min + " and " + max + ")"
            );
        }
    }
}
