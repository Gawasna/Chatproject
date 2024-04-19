package com.chatproject.controller;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxChecker {

    // Kiểm tra cú pháp của username và trả về true nếu hợp lệ, false nếu không hợp lệ
    // và truyền thông điệp lỗi qua tham số đầu ra
    public static boolean isUsernameValid(String username, StringBuilder errorMessage) {
        // Ít nhất 8 ký tự
        if (username.length() < 8) {
            errorMessage.append("Username phải chứa ít nhất 8 ký tự.\n");
            return false;
        }

        // Không chứa dấu cách và kí tự đặc biệt
        if (username.matches(".*\\s.*") || !username.matches("[a-zA-Z0-9_]+")) {
            errorMessage.append("Username không được chứa dấu cách hoặc kí tự đặc biệt.\n");
            return false;
        }

        // Ít nhất 1 chữ số và không bắt đầu bằng số
        if (!username.matches(".*[0-9].*") || Character.isDigit(username.charAt(0))) {
            errorMessage.append("Username phải chứa ít nhất 1 chữ số và không được bắt đầu bằng số.\n");
            return false;
        }

        // Ít nhất 5 chữ cái
        int letterCount = 0;
        for (char c : username.toCharArray()) {
            if (Character.isLetter(c)) {
                letterCount++;
            }
        }
        if (letterCount < 5) {
            errorMessage.append("Username phải chứa ít nhất 5 chữ cái.\n");
            return false;
        }

        return true; // Trả về true nếu không có lỗi
    }

    // Kiểm tra cú pháp của password và trả về true nếu hợp lệ, false nếu không hợp lệ
    // và truyền thông điệp lỗi qua tham số đầu ra
    public static boolean isPasswordValid(String password, String username, StringBuilder errorMessage) {
        // Ít nhất 8 ký tự
        if (password.length() < 8) {
            errorMessage.append("Password phải chứa ít nhất 8 ký tự.\n");
            return false;
        }

        // Không chứa dấu cách
        if (password.contains(" ")) {
            errorMessage.append("Password không được chứa dấu cách.\n");
            return false;
        }

        // Không trùng với username
        if (password.equals(username)) {
            errorMessage.append("Password không được trùng với username.\n");
            return false;
        }

        // Ít nhất 1 chữ cái in hoa, 1 chữ số và 1 ký tự đặc biệt
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*") || !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            errorMessage.append("Password phải chứa ít nhất 1 chữ cái in hoa, 1 chữ số và 1 ký tự đặc biệt.\n");
            return false;
        }

        return true; // Trả về true nếu không có lỗi
    }
}
