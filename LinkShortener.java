import java.util.HashMap;
import java.util.Random;

public class LinkShortener {
    private HashMap<String, String> urlMap; // shortURL to longURL
    private HashMap<String, String> reverseUrlMap; // longURL to shortURL
    private static final String BASE_URL = "http://short.ly/";
    private static final int SHORT_URL_LENGTH = 6;
    private Random random;

    public LinkShortener() {
        urlMap = new HashMap<>();
        reverseUrlMap = new HashMap<>();
        random = new Random();
    }

    public String shortenUrl(String longUrl) throws IllegalArgumentException {
        if (longUrl == null || longUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        // Check if URL already has a short version
        if (reverseUrlMap.containsKey(longUrl)) {
            return BASE_URL + reverseUrlMap.get(longUrl);
        }

        // Generate unique short URL
        String shortCode;
        do {
            shortCode = generateRandomString(SHORT_URL_LENGTH);
        } while (urlMap.containsKey(shortCode));

        urlMap.put(shortCode, longUrl);
        reverseUrlMap.put(longUrl, shortCode);

        return BASE_URL + shortCode;
    }

    public String expandUrl(String shortUrl) throws IllegalArgumentException {
        if (shortUrl == null || shortUrl.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        String shortCode = shortUrl.replace(BASE_URL, "");
        if (shortCode.length() != SHORT_URL_LENGTH) {
            throw new IllegalArgumentException("Invalid short URL format");
        }

        String longUrl = urlMap.get(shortCode);
        if (longUrl == null) {
            throw new IllegalArgumentException("Short URL not found");
        }

        return longUrl;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}