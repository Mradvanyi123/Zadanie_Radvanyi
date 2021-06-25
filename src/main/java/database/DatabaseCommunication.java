package database;

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

    public void select(String dateOfStatistics){
        String sql = "SELECT country, count(country)as visits FROM logins where dateOfLogin==? group by country ";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dateOfStatistics);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Country: "+rs.getString("country")+"  number of visits "+ rs.getInt("visits"));
            }
            System.out.println(rs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        DatabaseCommunication dbc=new DatabaseCommunication();
//        dbc.insert("HU","2012-12-01");
//        dbc.insert("HU","2012-12-01");
//        dbc.insert("HU","2012-12-01");
//
//        dbc.insert("SK","2012-12-01");
//        dbc.insert("SK","2012-12-01");
//
//        dbc.insert("EN","2012-12-01");
//        dbc.insert("EN","2012-12-01");
//        dbc.insert("EN","2012-12-01");
//        dbc.insert("EN","2012-12-01");
//        dbc.insert("EN","2012-12-01");
//        dbc.insert("EN","2012-12-01");
//
//
//        dbc.insert("US","2012-12-01");
//        dbc.insert("US","2012-12-02");
//        dbc.insert("US","2012-12-02");
//        dbc.insert("US","2012-12-02");
//        dbc.insert("US","2012-12-02");
//        dbc.insert("US","2012-12-02");
        dbc.select("2012-12-01");
    }
}