package api.pietunes.snoopy.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

public class TokenContainer {

    private final static long EXPIRATION_TIME = 3600;

    @Data
    static class Token {
        private String value;
        private long createdAt;

        public Token(String value) {
            this.value = value;
            this.createdAt = System.currentTimeMillis();
        }
    }
    
    private static final Map<String, Token> tokenMap = new ConcurrentHashMap<>();

    public static String getToken(String identifier) {
        Token token = tokenMap.get(identifier);

        if (token == null) return null;
        
        long currentTime = System.currentTimeMillis();
        
        if ((token.getCreatedAt() + EXPIRATION_TIME) >= currentTime) {
            invalidateToken(identifier);
            return null;
        } else {
            return token.getValue();
        }
    }

    public static void saveToken(String identifier, String token) {
        tokenMap.put(identifier, new Token(token));
    }

    public static void invalidateToken(String identifier) {
        tokenMap.remove(identifier);
    }
}
