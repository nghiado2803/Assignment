package ABC_news.Entity;

import java.util.List;

public class Category {
    private String id;         
    private String name;       
    private List<News> newsList; 

    public Category() {
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String id, String name, List<News> newsList) {
        this.id = id;
        this.name = name;
        this.newsList = newsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", newsListSize=" + (newsList != null ? newsList.size() : 0) +
                '}';
    }
}
