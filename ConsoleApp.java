import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        LinkShortener shortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Link Shortener Console Application");
        System.out.println("---------------------------------");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter URL to shorten: ");
                    String longUrl = scanner.nextLine();
                    try {
                        String shortUrl = shortener.shortenUrl(longUrl);
                        System.out.println("Shortened URL: " + shortUrl);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Enter short URL to expand: ");
                    String shortUrl = scanner.nextLine();
                    try {
                        String expandedUrl = shortener.expandUrl(shortUrl);
                        System.out.println("Original URL: " + expandedUrl);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}