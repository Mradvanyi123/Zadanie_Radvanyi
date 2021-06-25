package handlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.DatabaseCommunication;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginServiceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
        System.out.println("LoginServiceHandler");
        OutputStream os = t.getResponseBody();
        DatabaseCommunication dbc = new DatabaseCommunication();
        LocationServiceHandler lsh= new LocationServiceHandler();
        if (t.getRequestMethod().equalsIgnoreCase("POST")) {
            System.out.println("POST");
            String response;
            try {
                // get country byIp
                String countryName=lsh.getCountryFromLocation(getIpAsString(t.getRequestBody()));
                // insert to database
                dbc.insert(countryName,getDate());
                // set response
                response="logged from "+countryName;
            } catch (GeoIp2Exception e) { // TODO add better error handling
                e.printStackTrace();
                response=e.getLocalizedMessage();
            }
            t.sendResponseHeaders(200,response.getBytes(StandardCharsets.UTF_8).length);
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }else{
            System.out.println("Invalid request for /loginService : "+t.getRequestMethod() );
            t.sendResponseHeaders(404, 0);
        }
        os.close();
    }



    public String getIpAsString(InputStream body){
        String value = "{}";
        try {
            InputStreamReader isr =  new InputStreamReader(body, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            value = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = new JsonParser().parse(value).getAsJsonObject();
        System.out.println("body: "+value);
        return jsonObject.get("ip").getAsString() ;
    }

    public String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return  dateFormat.format(date);
    }
}
