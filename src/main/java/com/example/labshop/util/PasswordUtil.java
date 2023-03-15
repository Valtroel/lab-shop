package com.example.labshop.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    private static final int WORKLOAD = 12;

    public static String hashPassword(String passwordPlaintext) {
        String salt = BCrypt.gensalt(WORKLOAD);

        return BCrypt.hashpw(passwordPlaintext, salt);
    }

    public static boolean checkPassword(String passwordPlaintext, String storedHash) {
        boolean password_verified = false;

        if (null == storedHash || !storedHash.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(passwordPlaintext, storedHash);

        return password_verified;
    }
}
