package core;

import java.util.UUID;

public class TestData {

    public static final String NAME = "Test User";

    private TestData() {
    }

    public static String uniqueEmail() {
        return "qa-" + System.currentTimeMillis() + "-"
                + UUID.randomUUID().toString().substring(0,6) + "@example.com";
    }
}
