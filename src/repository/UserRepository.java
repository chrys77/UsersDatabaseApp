package repository;

import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {

    public ArrayList<User> readAll() {
        ArrayList<User> allUsers = new ArrayList<>();
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM user");
            while ((rs.next())) {
                allUsers.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public User readById(int id) {
        User userCitit;
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM user WHERE id = " + id);
            rs.next();
            userCitit = extractUserFromResultSet(rs);
        } catch (SQLException e) {
            return null;
        }
        return userCitit;
    }

    public boolean create(String lastName, String firstName, String email, String phoneNumber) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "INSERT INTO user (last_name, first_name, email, phone_number) VALUES ('%s', '%s', '%s', '%s');";
            int affectedRows = st.executeUpdate(String.format(template, lastName, firstName, email, phoneNumber));
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("The user was not saved");
            return false;
        }
    }

    public void modifyName(int id, String newLastName) {
        modifyColumn(id, newLastName, "last_name");
    }

    public void modifyPrenume(int id, String newFirstName) {
        modifyColumn(id, newFirstName, "first_name");
    }

    public void modifyEmail(int id, String newEmail) {
        modifyColumn(id, newEmail, "email");
    }

    public void modifyPhoneNumber(int id, String newPhoneNumber) {
        modifyColumn(id, newPhoneNumber, "phone_number");
    }

    public boolean delete(int id) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "DELETE FROM user WHERE id = %d";
            int affectedRows = st.executeUpdate(String.format(template, id));
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("The user has not been deleted");
            return false;
        }
    }


    private static User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                rs.getString("email"),
                rs.getString("phone_number")
        );
    }

    private static void modifyColumn(int id, String newValues, String columnName) {
        Connection c = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement st = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE user SET %s = '%s' WHERE id = %d";
            int affectedRows = st.executeUpdate(String.format(template, columnName, newValues, id));
            System.out.println(columnName + (affectedRows > 0 ? " changed" : " unchanged"));
        } catch (SQLException e) {
            System.out.println("The column could not be modified");
        }
    }


}
