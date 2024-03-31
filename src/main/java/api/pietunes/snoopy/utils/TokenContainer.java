package api.pietunes.snoopy.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenContainer {
    private static final Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public static String getToken(String identifier) {
        return tokenMap.get(identifier);
    }

    public static void saveToken(String identifier, String token) {
        tokenMap.put(identifier, token);
    }

    public static void invalidateToken(String identifier) {
        tokenMap.remove(identifier);
    }
}
