package ru.lkodos.servlet_util;

public class RequestValidator {

    private RequestValidator() {
    }

    public static boolean validate(String code) {

        if (code.length() != 3) {
            return false;
        }
        char[] charArray = code.toUpperCase().toCharArray();
        for (char ch : charArray) {
            if (!(ch >= 'A' && ch <= 'Z')) {
                return false;
            }
        }
        return true;
    }
}