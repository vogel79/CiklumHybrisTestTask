package utils;

import java.util.Random;

public class UserIdGenerator {
    public static final int MAX_USER_ID = 100;
    private final Random random = new Random();

    public int generate() {
        return random.nextInt(MAX_USER_ID + 1);
    }
}
