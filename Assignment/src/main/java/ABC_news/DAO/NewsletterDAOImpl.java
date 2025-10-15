package ABC_news.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ABC_news.Utils.Jdbc;

public class NewsletterDAOImpl implements NewsletterDAO {

    @Override
    public boolean addSubscriber(String email) {
        String sql = "INSERT INTO NEWSLETTERS (Email, Enabled) VALUES (?, 1)";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean exists(String email) {
        String sql = "SELECT 1 FROM NEWSLETTERS WHERE Email = ?";
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
    public List<String> getActiveEmails() {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT Email FROM NEWSLETTERS WHERE Enabled = 1";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                emails.add(rs.getString("Email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

}
