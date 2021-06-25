package services;

import com.sun.net.httpserver.HttpServer;
import handlers.LoginServiceHandler;
import handlers.StatisticsHandler;

import java.net.InetSocketAddress;

public class LoginLogsAggService {

    public static void main(String[] args) throws Exception {


        System.out.println("LoginLogsAggService");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/loginService", new LoginServiceHandler());
        server.createContext("/statistics", new StatisticsHandler());
        //Thread control is given to executor service.
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();



    }

}
