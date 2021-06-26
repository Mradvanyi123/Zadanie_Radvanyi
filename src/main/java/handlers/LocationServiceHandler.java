package handlers;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;

public  class LocationServiceHandler  {
    public String getCountryFromLocation(String ip) throws IOException, GeoIp2Exception {
//        System.out.println("getCountryFromLocation");
        String dbLocation = "/home/radvanyi/IdeaProjects/Zadanie/src/main/java/GeoLite2-City_20210615/GeoLite2-City.mmdb";

        File database = new File(dbLocation);
        DatabaseReader dbReader;
        String country;
        try {
            dbReader = new DatabaseReader.Builder(database).build();
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = dbReader.city(ipAddress);
            String countryName = response.getCountry().getName();
            country= convertCountryNameToISOCode(countryName);

        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
            throw e;
        }
        return country;
    }
    public String convertCountryNameToISOCode(String countryName){
//        System.out.println("convertCountryNameToISOCode");
        String countryCode="";
        String[] countryCodes = Locale.getISOCountries();
        for (String cd : countryCodes){
            Locale locale = new Locale("", cd);
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();
            if(name.equals(countryName)){
                countryCode=code;
            }
        }
        return countryCode;
    }
}

