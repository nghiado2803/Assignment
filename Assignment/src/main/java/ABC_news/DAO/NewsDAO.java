package ABC_news.DAO;

import java.util.List;
import ABC_news.Entity.News;

public interface NewsDAO {
    List<News> findAll();                            // Lấy tất cả tin tức
    News findById(String id);                        // Tìm tin theo ID
    List<News> findByCategory(String categoryId);    // Tìm tin theo loại
    List<News> searchByTitle(String keyword);        // Tìm tin theo tiêu đề
    boolean insert(News news);                       // Thêm tin mới
    boolean update(News news);                       // Cập nhật tin
    boolean delete(String id); 						 // Xóa tin
    List<News> searchNews(String keyword);
    List<News> selectByAuthor(String authorId);
    boolean updateStatus(String id, String newStatus);
    List<News> findTopViewedApproved(int limit);
    List<News> findLatestApproved(int limit);
    List<News> findApprovedByCategory(String categoryId);
    List<News> getApprovedNewsByCategoryName(String categoryName);
    public News getNewsById(String id);
    List<News> findHomeByPosition(int position);
    News getById(String id);
    void updateFeature(String id, boolean value);
    void updateApproved(String id, boolean value);
    void updateEmailed(String id, boolean value);
    public void incrementViewCount(String id);
    List<News> getAllNews();
}
