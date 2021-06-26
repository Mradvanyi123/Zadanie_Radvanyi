package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.DatabaseCommunication;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class StatisticsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t )throws IOException {
//        System.out.println("statistics");
        DatabaseCommunication dbc =new DatabaseCommunication();
        OutputStream os = t.getResponseBody();

        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
           String response;
           Map<String,String> queryToMap =queryToMap(t.getRequestURI().getQuery());
           response=dbc.select(queryToMap.get("date"), Integer.valueOf(queryToMap.get("amountOfResults")));
            System.out.println("response "+response);
            t.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
        }else{
            System.out.println("Invalid request for /statistics : "+t.getRequestMethod() );
            t.sendResponseHeaders(404, 0);
        }
        os.close();
    }

    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
