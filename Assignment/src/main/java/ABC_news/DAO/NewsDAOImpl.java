package ABC_news.DAO;

import ABC_news.Entity.News;
import ABC_news.Utils.Jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDAOImpl implements NewsDAO {

    @Override
    public List<News> findAll() {
        List<News> list = new ArrayList<>();
        String sql = """
            SELECT n.*, c.Name AS CategoryName
            FROM NEWS n
            LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id
            ORDER BY n.PostedDate DESC
            """;
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                News n = mapResultSetToNews(rs);
                n.setCategoryName(rs.getNString("CategoryName"));
                list.add(n);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public News findById(String id) {
        String sql = """
            SELECT n.*, c.Name AS CategoryName
            FROM NEWS n
            LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id
            WHERE n.Id = ?
            """;
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    News n = mapResultSetToNews(rs);
                    n.setCategoryName(rs.getNString("CategoryName"));
                    return n;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<News> findByCategory(String categoryId) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM NEWS WHERE CategoryId = ? ORDER BY PostedDate DESC";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNews(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<News> searchByTitle(String keyword) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM NEWS WHERE Title LIKE ? ORDER BY PostedDate DESC";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToNews(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean insert(News news) {
        String sql = """
            INSERT INTO NEWS (Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home, Position, Status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, news.getId());
            ps.setNString(2, news.getTitle());
            ps.setNString(3, news.getContent());
            ps.setString(4, news.getImage());
            ps.setDate(5, news.getPostedDate() == null ? new java.sql.Date(System.currentTimeMillis()) : news.getPostedDate());
            ps.setString(6, news.getAuthor());
            ps.setInt(7, news.getViewCount());
            ps.setString(8, news.getCategoryId());
            ps.setBoolean(9, news.isHome());
            if (news.getPosition() != null) {
                ps.setInt(10, news.getPosition());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }
            if (news.getStatus() != null) {
                ps.setNString(11, news.getStatus());
            } else {
                ps.setNString(11, "Chưa duyệt");
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(News news) {
        String sql = """
            UPDATE NEWS
            SET Title = ?, Content = ?, Image = ?, PostedDate = ?, Author = ?, ViewCount = ?, CategoryId = ?, Home = ?, Position = ?, Status = ?
            WHERE Id = ?
            """;
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, news.getTitle());
            ps.setNString(2, news.getContent());
            ps.setString(3, news.getImage());
            ps.setDate(4, news.getPostedDate() == null ? new java.sql.Date(System.currentTimeMillis()) : news.getPostedDate());
            ps.setString(5, news.getAuthor());
            ps.setInt(6, news.getViewCount());
            ps.setString(7, news.getCategoryId());
            ps.setBoolean(8, news.isHome());
            if (news.getPosition() != null) {
                ps.setInt(9, news.getPosition());
            } else {
                ps.setNull(9, java.sql.Types.INTEGER);
            }
            ps.setNString(10, news.getStatus() != null ? news.getStatus() : "Chưa duyệt");
            ps.setString(11, news.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM NEWS WHERE Id = ?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private News mapResultSetToNews(ResultSet rs) throws SQLException {
        News n = new News();
        n.setId(rs.getString("Id"));
        n.setTitle(rs.getNString("Title"));
        n.setContent(rs.getNString("Content"));
        n.setImage(rs.getString("Image"));
        n.setPostedDate(rs.getDate("PostedDate"));
        n.setAuthor(rs.getString("Author"));
        n.setViewCount(rs.getInt("ViewCount"));
        n.setCategoryId(rs.getString("CategoryId"));
        n.setHome(rs.getBoolean("Home"));
        int pos = rs.getInt("Position");
        if (rs.wasNull()) {
            n.setPosition(null);
        } else {
            n.setPosition(pos);
        }

        try {
            n.setStatus(rs.getNString("Status"));
        } catch (SQLException ex) {
            n.setStatus(null);
        }

        try {
            n.setEmailed(rs.getBoolean("isEmailed"));
        } catch (SQLException ex) {
            n.setEmailed(false);
        }

        return n;
    }


    @Override
    public List<News> searchNews(String keyword) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT n.*, c.Name AS CategoryName FROM NEWS n LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id WHERE n.Title LIKE ? OR n.Content LIKE ? ORDER BY n.PostedDate DESC";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String kw = "%" + keyword.trim() + "%";
            ps.setNString(1, kw);
            ps.setNString(2, kw);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    News n = mapResultSetToNews(rs);
                    n.setCategoryName(rs.getNString("CategoryName"));
                    list.add(n);
                }
            }

            System.out.println(">>> Tìm thấy " + list.size() + " kết quả cho từ khóa: " + keyword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<News> selectByAuthor(String authorId) {
        String sql = "SELECT n.*, c.Name AS CategoryName FROM NEWS n LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id WHERE n.Author = ? ORDER BY n.PostedDate DESC";
        List<News> list = new ArrayList<>();
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    News n = mapResultSetToNews(rs);
                    n.setCategoryName(rs.getNString("CategoryName"));
                    list.add(n);
                }
            }
            System.out.println(">>> selectByAuthor(" + authorId + "): Tìm thấy " + list.size() + " tin");
        } catch (SQLException e) {
            System.out.println(">>> Lỗi selectByAuthor(" + authorId + "): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<News> findTopViewed(int limit) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT TOP (?) n.*, c.Name AS CategoryName FROM NEWS n LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id ORDER BY n.ViewCount DESC, n.PostedDate DESC";
        // NOTE: some DB (SQL Server) uses different TOP syntax with parameter; if your driver doesn't support TOP(?), use FETCH
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT n.*, c.Name AS CategoryName FROM NEWS n LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id ORDER BY n.ViewCount DESC, n.PostedDate DESC")) {
            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                while (rs.next() && count < limit) {
                    News n = mapResultSetToNews(rs);
                    n.setCategoryName(rs.getNString("CategoryName"));
                    list.add(n);
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<News> findLatest(int limit) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT n.*, c.Name AS CategoryName FROM NEWS n LEFT JOIN CATEGORIES c ON n.CategoryId = c.Id ORDER BY n.PostedDate DESC";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int count = 0;
            while (rs.next() && count < limit) {
                News n = mapResultSetToNews(rs);
                n.setCategoryName(rs.getNString("CategoryName"));
                list.add(n);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public boolean updateStatus(String id, String newStatus) {
        String sql = "UPDATE News SET status = ? WHERE id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setString(2, id);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<News> findTopViewedApproved(int limit) {
        List<News> list = new ArrayList<>();
        String sql = """
            SELECT TOP (?) n.*, c.name AS categoryName
            FROM NEWS n
            JOIN CATEGORIES c ON n.category_id = c.id
            WHERE n.status = N'Đã duyệt'
            ORDER BY n.view_count DESC
        """;

        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                News n = new News();
                n.setId(rs.getString("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setImage(rs.getString("image"));
                n.setPostedDate(rs.getDate("posted_date"));
                n.setAuthor(rs.getString("author"));
                n.setViewCount(rs.getInt("view_count"));
                n.setCategoryId(rs.getString("category_id"));
                n.setCategoryName(rs.getString("categoryName"));
                n.setHome(rs.getBoolean("home"));
                n.setPosition(rs.getInt("position"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<News> findLatestApproved(int limit) {
        List<News> list = new ArrayList<>();
        String sql = """
            SELECT TOP (?) n.*, c.name AS categoryName
            FROM NEWS n
            JOIN CATEGORIES c ON n.category_id = c.id
            WHERE n.status = N'Đã duyệt'
            ORDER BY n.posted_date DESC
        """;

        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                News n = new News();
                n.setId(rs.getString("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setImage(rs.getString("image"));
                n.setPostedDate(rs.getDate("posted_date"));
                n.setAuthor(rs.getString("author"));
                n.setViewCount(rs.getInt("view_count"));
                n.setCategoryId(rs.getString("category_id"));
                n.setCategoryName(rs.getString("categoryName"));
                n.setHome(rs.getBoolean("home"));
                n.setPosition(rs.getInt("position"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<News> findApprovedByCategory(String categoryId) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT * FROM NEWS WHERE CategoryId = ? AND Status = N'Đã duyệt' ORDER BY PostedDate DESC";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToNews(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<News> getApprovedNewsByCategoryName(String categoryName) {
        List<News> list = new ArrayList<>();
        String sql = """
            SELECT n.Id, n.Title, n.Content, n.Image, n.PostedDate, 
                   n.Author, n.ViewCount, n.CategoryId, c.Name AS CategoryName, 
                   n.Home, n.Position, n.Status
            FROM NEWS n
            JOIN CATEGORIES c ON n.CategoryId = c.Id
            WHERE c.Name = ? AND n.Status = N'Đã duyệt'
            ORDER BY n.PostedDate DESC
        """;

        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoryName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    News news = new News();
                    news.setId(rs.getString("Id"));
                    news.setTitle(rs.getString("Title"));
                    news.setContent(rs.getString("Content"));
                    news.setImage(rs.getString("Image"));
                    news.setPostedDate(rs.getDate("PostedDate"));
                    news.setAuthor(rs.getString("Author"));
                    news.setViewCount(rs.getInt("ViewCount"));
                    news.setCategoryId(rs.getString("CategoryId"));
                    news.setCategoryName(rs.getString("CategoryName"));
                    news.setHome(rs.getBoolean("Home"));
                    news.setPosition(rs.getInt("Position"));
                    news.setStatus(rs.getString("Status"));

                    list.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public News getNewsById(String id) {
        String sql = "SELECT * FROM NEWS WHERE Id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                News n = new News();
                n.setId(rs.getString("Id"));
                n.setTitle(rs.getString("Title"));
                n.setContent(rs.getString("Content"));
                n.setImage(rs.getString("Image"));
                n.setPostedDate(rs.getDate("PostedDate"));
                n.setAuthor(rs.getString("Author"));
                n.setCategoryId(rs.getString("CategoryId"));
                n.setViewCount(rs.getInt("ViewCount"));
                n.setHome(rs.getBoolean("Home"));
                n.setPosition(rs.getInt("Position"));
                n.setStatus(rs.getString("Status"));
                return n;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<News> findHomeByPosition(int position) {
        List<News> list = new ArrayList<>();
        String sql = "SELECT Id, Title, Content, Image, PostedDate, Author, ViewCount, CategoryId, Home, Position, Status " +
                     "FROM NEWS " +
                     "WHERE Home = 1 AND Position = ? AND Status = N'Đã duyệt' " +
                     "ORDER BY PostedDate DESC";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, position);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    News n = new News();
                    n.setId(rs.getString("Id"));
                    n.setTitle(rs.getString("Title"));
                    n.setContent(rs.getString("Content"));
                    n.setImage(rs.getString("Image"));

                    java.sql.Date pd = rs.getDate("PostedDate");
                    n.setPostedDate(pd);

                    n.setAuthor(rs.getString("Author"));

                    n.setViewCount(rs.getInt("ViewCount"));

                    n.setCategoryId(rs.getString("CategoryId"));

                    n.setHome(rs.getBoolean("Home"));

                    int pos = rs.getInt("Position");
                    if (rs.wasNull()) {
                        n.setPosition(null);
                    } else {
                        n.setPosition(pos);
                    }

                    n.setStatus(rs.getString("Status"));

                    list.add(n);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public News getById(String id) {
        String sql = "SELECT * FROM NEWS WHERE Id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                News n = new News();
                n.setId(rs.getString("Id"));
                n.setTitle(rs.getString("Title"));
                n.setContent(rs.getString("Content"));
                n.setImage(rs.getString("Image"));
                n.setPostedDate(rs.getDate("PostedDate"));
                n.setAuthor(rs.getString("Author"));
                n.setViewCount(rs.getInt("ViewCount"));
                n.setCategoryId(rs.getString("CategoryId"));
                n.setHome(rs.getBoolean("Home"));
                n.setPosition(rs.getInt("Position"));
                n.setStatus(rs.getString("Status"));
                return n;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void updateFeature(String id, boolean value) {
        String sql = "UPDATE NEWS SET isFeatured = ? WHERE Id = ?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, value);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateApproved(String id, boolean value) {
        String sql = "UPDATE NEWS SET isApproved = ?, Status = N'Đã duyệt' WHERE Id = ?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, value);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmailed(String id, boolean value) {
        String sql = "UPDATE NEWS SET isEmailed = ? WHERE Id = ?";
        try (Connection con = Jdbc.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, value);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void incrementViewCount(String id) {
        String sql = "UPDATE NEWS SET ViewCount = ISNULL(ViewCount, 0) + 1 WHERE Id = ?";
        try (Connection conn = Jdbc.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Đã tăng lượt xem cho tin có ID: " + id);
            } else {
                System.out.println("Không tìm thấy tin có ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật lượt xem: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
