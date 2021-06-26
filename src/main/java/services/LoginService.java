package services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class LoginService {
//    TODO add interface for setting  login(post request rate)
    public static void SendPostRequest() {
//        System.out.println("SendPostRequest");
        URL url;
        try {
            url = new URL("http://127.0.0.1:8000/loginService");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

//          String so=MessageFormat.format("{ \"ts\": {0} \"ip\": \"87.244.211.47\" }", randomTS());
//          byte[] out = (so.getBytes(StandardCharsets.UTF_8));
//            TODO add randomTS() , create random ip generator
            byte[] out = "{ \"ts\":1614281031 \"ip\": \"87.244.211.47\" }" .getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
//        TODO process response (Do something with http.getInputStream())

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String randomTS(){
        StringBuilder tsString= new StringBuilder();
        int min = 0, max=9;
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        for(int i=0;i<=9;i++){
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            tsString.append(random_int);
        }
        return tsString.toString();
    }

    public static void main(String[] args) {
        LoginService.SendPostRequest();
    }
}

