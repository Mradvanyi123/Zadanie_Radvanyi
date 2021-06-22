package services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginService {
    public static void SendPostRequest(){
        System.out.println("SendPostRequest");
        try {
            URL url = new URL ("https://reqres.in/api/users");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

//            private String jsonInputString = "{"name": "Upendra", "job": "Programmer"}";
            String jsonInputString = "{ \"name\": \"Baeldung\", \"java\": true }";
//            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
