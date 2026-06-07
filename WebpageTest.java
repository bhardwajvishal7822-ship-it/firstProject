import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebpageTest {
    public static void main(String[] args) {
        String targetUrl = "http://localhost:8083";
        System.out.println("🔍 Java Testing Shuru Ho Rahi Hai...");

        try {
            // 1. Connection set up karna
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 2. Status code check karna
            int statusCode = connection.getResponseCode();
            System.out.println("✅ Status Code: " + statusCode + " OK!");

            // 3. Webpage ka poora HTML code read (download) karna
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            String htmlContent = content.toString();

            // 4. ASLI TEST CASES (Galti pakadne wala zone)
            boolean test1 = htmlContent.contains("Mubarak Ho Bhai!");
            boolean test2 = htmlContent.contains("Docker Container");

            if (test1) {
                System.out.println("✅ Test 1 Pass: 'Mubarak Ho Bhai!' text mil gaya!");
            } else {
                System.out.println("❌ Test 1 Fail: 'Mubarak Ho Bhai!' text missing hai!");
                System.exit(1); // Jenkins ko fail ka signal bhejna
            }

            if (test2) {
                System.out.println("✅ Test 2 Pass: 'Docker Container' text mil gaya!");
            } else {
                System.out.println("❌ Test 2 Fail: 'Docker Container' text missing hai!");
                System.exit(1); // Jenkins ko fail ka signal bhejna
            }

            System.out.println("🎉 SAARE TEST CASES PASS! Jenkins ko Green Signal bhej rahe hain.");
            System.exit(0); // Jenkins ko SUCCESS ka signal bhejna

        } catch (Exception e) {
            System.out.println("❌ Server Connection Fail: Kya docker container chal raha hai? Error: " + e.getMessage());
            System.exit(1);
        }
    }
}