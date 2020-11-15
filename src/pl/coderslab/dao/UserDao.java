package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.tables.User;
import pl.coderslab.workshop.DbUtil;

import java.sql.*;
import java.util.Arrays;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {
    public static String CREATE_USER_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?);";
    public static String READ_ROW_USER = "SELECT * FROM users WHERE id = ?;";
    public static String UPDATE_ROW_USER = "UPDATE users SET email = ?, username = ?, password = ? WHERE id = ?;";
    public static String DELETE_ROW_USER = "DELETE FROM users WHERE id = ?;";
    public static String ALL_ROWS_USER = "SELECT * FROM users;";
    public static String DATABASE_WS2 = "workshop2";

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection(DATABASE_WS2);
             PreparedStatement prepStat =
                     conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);) {
            prepStat.setString(1, user.getEmail());
            prepStat.setString(2, user.getUsername());
            prepStat.setString(3, hashPassword(user.getPassword()));
            prepStat.executeUpdate();

            setUserIdGeneratedFromDatabase(user, prepStat);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setUserIdGeneratedFromDatabase(User user, PreparedStatement prepStat) throws SQLException {
        ResultSet databaseIdRS = prepStat.getGeneratedKeys();
        if (databaseIdRS.next()) {
            long generatedId = databaseIdRS.getLong(1);
            user.setId(generatedId);
            System.out.println("Inserted ID: " + generatedId);
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection(DATABASE_WS2);
             PreparedStatement prepStat = conn.prepareStatement(READ_ROW_USER);) {
            prepStat.setInt(1, userId);
            ResultSet userRow = prepStat.executeQuery();
            User user = new User();
            while (userRow.next()) {
                user.setId(userRow.getInt(1));
                user.setEmail(userRow.getString(2));
                user.setUsername(userRow.getString(3));
                user.setPassword(userRow.getString(4));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection(DATABASE_WS2);
             PreparedStatement prepStat = conn.prepareStatement(UPDATE_ROW_USER)) {
            prepStat.setString(1, user.getEmail());
            prepStat.setString(2, user.getUsername());
            prepStat.setString(3, user.getPassword());
            prepStat.setLong(4, user.getId());
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (PreparedStatement prepStat = DbUtil.getConnection(DATABASE_WS2).prepareStatement(DELETE_ROW_USER);) {
            prepStat.setInt(1, userId);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Statement stat = DbUtil.getConnection(DATABASE_WS2).createStatement();) {
            ResultSet allRows = stat.executeQuery(ALL_ROWS_USER);
            User[] users = new User[0];
            User user = new User();
            int i = 0;
            while (allRows.next()) {
                user.setId(allRows.getLong("id"));
                user.setEmail(allRows.getString("email"));
                user.setUsername(allRows.getString("username"));
                user.setPassword(allRows.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User[] addToArray (User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length +1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

}