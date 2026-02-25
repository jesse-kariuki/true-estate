package com.trustestate.backend.common.util;

import java.security.SecureRandom;

public class IdGeneratorUtil {

    private static final String PREFIX = "TRST-";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;
    private static final SecureRandom random = new SecureRandom();

    public static String generateSystemUserId() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return PREFIX + sb.toString();
    }
}
