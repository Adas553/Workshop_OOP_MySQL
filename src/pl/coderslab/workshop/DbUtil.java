package pl.coderslab.workshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class DbUtil {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/products_ex?useSSL=false&characterEncoding=utf8";
    public static final String DB_URL_PATTERN = "jdbc:mysql://localhost:3306/%s?useSSL=false&characterEncoding=utf8";
    public static final String USER = "adam";
    public static final String PASSWORD = "coderslab";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    public static Connection getConnection(String database) throws SQLException {
        String dbURL = String.format(DB_URL_PATTERN, database);
        return DriverManager.getConnection(dbURL, USER, PASSWORD);
    }
    public static void insert(String database, String query, String... params) {
        try (PreparedStatement prepStat = getConnection(database).prepareStatement(query);){
            for (int i = 0; i < params.length; i++) {
                prepStat.setString(i + 1, params[i]);
            }
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
