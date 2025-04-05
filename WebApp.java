import static spark.Spark.*;

public class WebApp {
    public static void main(String[] args) {
        LinkShortener shortener = new LinkShortener();

        // Configure port
        port(8080);

        // Serve static files (HTML, CSS, JS)
        staticFiles.location("/public");

        // API Endpoints
        post("/api/shorten", (req, res) -> {
            String longUrl = req.queryParams("url");
            if (longUrl == null || longUrl.isEmpty()) {
                res.status(400);
                return "{\"error\": \"URL is required\"}";
            }
            
            try {
                String shortUrl = shortener.shortenUrl(longUrl);
                return "{\"shortUrl\": \"" + shortUrl + "\"}";
            } catch (IllegalArgumentException e) {
                res.status(400);
                return "{\"error\": \"" + e.getMessage() + "\"}";
            }
        });

        get("/api/expand", (req, res) -> {
            String shortUrl = req.queryParams("url");
            if (shortUrl == null || shortUrl.isEmpty()) {
                res.status(400);
                return "{\"error\": \"URL is required\"}";
            }
            
            try {
                String longUrl = shortener.expandUrl(shortUrl);
                return "{\"longUrl\": \"" + longUrl + "\"}";
            } catch (IllegalArgumentException e) {
                res.status(400);
                return "{\"error\": \"" + e.getMessage() + "\"}";
            }
        });

        get("/:shortCode", (req, res) -> {
            String shortCode = req.params(":shortCode");
            if (shortCode.length() != 6) {
                res.status(404);
                return "Invalid short URL";
            }
            
            try {
                String longUrl = shortener.expandUrl(LinkShortener.BASE_URL + shortCode);
                res.redirect(longUrl);
                return null;
            } catch (IllegalArgumentException e) {
                res.status(404);
                return "Short URL not found";
            }
        });
    }
}