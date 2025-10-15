package ABC_news.DAO;

import ABC_news.Entity.Category;
import java.util.List;

public interface CategoryDAO {
    List<Category> findAll();
    Category findById(String id);
    boolean insert(Category category);
    boolean update(Category category);
    boolean delete(String id);
}
