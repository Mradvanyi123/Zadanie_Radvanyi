package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class StatisticsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t )throws IOException {
        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            System.out.println("StatisticsHandler");
            String response ="Statistics";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }else{
            System.out.println("Invalid request for /statistics : "+t.getRequestMethod() );
            t.sendResponseHeaders(404, 0);
            OutputStream os = t.getResponseBody();
            os.close();
        }

    }
}
