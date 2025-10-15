package ABC_news.Utils;

import java.sql.*;

public class Jdbc {
    // Cấu hình thông tin kết nối
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ABC_News;encrypt=false";
    private static final String USER = "vannghia";
    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName(DRIVER);
            System.out.println("Đã nạp driver SQL Server thành công!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy Driver SQL Server!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static int executeUpdate(String sql, Object... args) {
        try (PreparedStatement stmt = prepareStatement(sql, args)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực thi câu lệnh: " + sql, e);
        }
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi truy vấn: " + sql, e);
        }
    }

    private static PreparedStatement prepareStatement(String sql, Object... args) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt;

        if (sql.trim().startsWith("{")) {
            stmt = conn.prepareCall(sql);
        } else {
            stmt = conn.prepareStatement(sql);
        }

        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException ignored) {}
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException ignored) {}
        try {
            if (conn != null) conn.close();
        } catch (SQLException ignored) {}
    }
}