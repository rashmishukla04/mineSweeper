package main.service;

public class InputValidator {
    public static boolean isValid(String input, int size) {
        if (input.length() < 2) return false;
        char row = input.charAt(0);
        if (row < 'A' || row >= 'A' + size) return false;
        try {
            int col = Integer.parseInt(input.substring(1));
            return col >= 1 && col <= size;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}