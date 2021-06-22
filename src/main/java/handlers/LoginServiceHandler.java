package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class LoginServiceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        System.out.println("LoginServiceHandler");
        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            System.out.println("POST");
            String response ="LoginServiceHandler";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }else{
            System.out.println("Invalid request for /loginService : "+t.getRequestMethod() );
            t.sendResponseHeaders(404, 0);
            OutputStream os = t.getResponseBody();
            os.close();
        }

    }
}
