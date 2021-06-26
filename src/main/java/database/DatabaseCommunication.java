package database;


import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;

public class DatabaseCommunication {
    /**
     * Connect to a database
     */
    private Connection connect() {
        // SQLite connection string
            String url = "jdbc:sqlite:/home/radvanyi/IdeaProjects/Zadanie/src/main/java/database/database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String country, String dateOfLogin) {
//        System.out.println("insert");
        String sql = "INSERT INTO logins(country,dateOfLogin) VALUES(?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, country);
            pstmt.setString(2, dateOfLogin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String select(String dateOfStatistics,Integer limitOfResults){
        String sql = "SELECT dateOfLogin,country, count(country)as logins FROM logins where dateOfLogin==? group by country limit ?";
        String result = null;
        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,dateOfStatistics);
            pstmt.setInt(2,limitOfResults);
//            System.out.println(pstmt);
            ResultSet rs  = pstmt.executeQuery();

            JSONArray jsonArray = new JSONArray();
            int order=1;
            while (rs.next()) {
//                System.out.println("Date: "+rs.getString("dateOfLogin")+"Country: "+rs.getString("country")+"  number of logins "+ rs.getInt("logins"));
                JSONObject obj = new JSONObject();
                obj.put("date",rs.getString("dateOfLogin"));
                obj.put("order",order);
                obj.put("country",rs.getString("country"));
                obj.put("logins",rs.getString("logins"));
                order++;
                jsonArray.put(obj);
            }

            result=jsonArray.toString();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}