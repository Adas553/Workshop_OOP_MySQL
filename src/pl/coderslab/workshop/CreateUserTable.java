package pl.coderslab.workshop;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUserTable {
    public static String CREATE_USER = "CREATE TABLE users(\n" +
            "    id INT AUTO_INCREMENT,\n" +
            "    email VARCHAR(255) UNIQUE,\n" +
            "    username VARCHAR(255),\n" +
            "    password VARCHAR(60),\n" +
            "    PRIMARY KEY (id)\n" +
            ");";

    public static String DATABASE = "workshop2";
    public static void main(String[] args) {
        try (Connection conn = DbUtil.getConnection("workshop2");
             Statement stat = conn.createStatement();) {
            stat.executeUpdate(CREATE_USER);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
