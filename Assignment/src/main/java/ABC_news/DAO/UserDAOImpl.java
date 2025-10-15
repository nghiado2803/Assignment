package ABC_news.DAO;

import ABC_news.Utils.*;
import ABC_news.Entity.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean insert(User user) {
        String sql = "INSERT INTO USERS (Id, Password, Fullname, Birthday, Gender, Mobile, Email, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setDate(4, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            ps.setBoolean(5, user.isGender());
            ps.setString(6, user.getMobile());
            ps.setString(7, user.getEmail());
            ps.setBoolean(8, user.isRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String email, String password) {
        String sql = "SELECT * FROM USERS WHERE Email = ? AND Password = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setBirthday(rs.getDate("Birthday") != null ? rs.getDate("Birthday").toLocalDate() : null);
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM USERS WHERE Email = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try (Connection conn = Jdbc.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setBirthday(rs.getDate("Birthday") != null ? rs.getDate("Birthday").toLocalDate() : null);
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public User findById(String id) {
        String sql = "SELECT * FROM USERS WHERE Id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setBirthday(rs.getDate("Birthday") != null ? rs.getDate("Birthday").toLocalDate() : null);
                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE USERS SET Password = ?, Fullname = ?, Birthday = ?, Gender = ?, Mobile = ?, Email = ?, Role = ? WHERE Id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFullname());
            ps.setDate(3, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            ps.setBoolean(4, user.isGender());
            ps.setString(5, user.getMobile());
            ps.setString(6, user.getEmail());
            ps.setBoolean(7, user.isRole());
            ps.setString(8, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM USERS WHERE Id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean updatePassword(String userId, String newPassword) {
        String sql = "UPDATE USERS SET Password=? WHERE Id=?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USERS";

        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getString("Id"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));

                Date sqlDate = rs.getDate("Birthday");
                if (sqlDate != null) {
                    u.setBirthday(sqlDate.toLocalDate());
                }

                u.setGender(rs.getBoolean("Gender"));
                u.setMobile(rs.getString("Mobile"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getBoolean("Role"));

                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
