package ABC_news.Entity;

import java.sql.Date;

public class News {
    private String id;
    private String title;
    private String content;
    private String image;
    private Date postedDate;
    private String author;
    private int viewCount;
    private String categoryId;
    private String categoryName;
    private boolean home;
    private Integer position;
    private String status;
    private boolean isFeatured;
    private boolean isApproved;
    private boolean isEmailed;
    private String authorName;

    public News() {}

    public News(String id, String title, String content, String image, Date postedDate,
                String author, int viewCount, String categoryId, boolean home, Integer position, String status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.postedDate = postedDate;
        this.author = author;
        this.viewCount = viewCount;
        this.categoryId = categoryId;
        this.home = home;
        this.position = position;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Date getPostedDate() { return postedDate; }
    public void setPostedDate(Date postedDate) { this.postedDate = postedDate; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public boolean isHome() { return home; }
    public void setHome(boolean home) { this.home = home; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSummary() {
        if (content == null) return "";
        return content.length() > 120 ? content.substring(0, 120) + "..." : content;
    }

    public Date getPublishDate() {
        return postedDate;
    }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean isFeatured) { this.isFeatured = isFeatured; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean isApproved) { this.isApproved = isApproved; }

    public boolean isEmailed() { return isEmailed; }
    public void setEmailed(boolean isEmailed) { this.isEmailed = isEmailed; }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
