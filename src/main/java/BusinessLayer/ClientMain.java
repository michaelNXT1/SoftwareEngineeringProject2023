package BusinessLayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        System.out.println("Welcome to our Market! would you like to come in? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String initInput = scanner.nextLine();
        String[] inputs = new String[]{"y", "n"};
        while (!Arrays.asList(inputs).contains(initInput.trim().toLowerCase())) {
            System.out.print("Invalid input, try again: ");
            initInput = scanner.nextLine();
        }
        if(initInput.trim().equalsIgnoreCase("y")){
            beginServerLoop(scanner);
        }
    }

    private static void beginServerLoop(Scanner scanner) {
        try {
            URL url = new URL("http://localhost:8080/echo");

            while (true) {
                String input = scanner.nextLine();
                if (input.equals("exit")) {
                    break;
                }
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(input);
                out.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = in.readLine();
                System.out.println("Server response: " + response);

                in.close();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
