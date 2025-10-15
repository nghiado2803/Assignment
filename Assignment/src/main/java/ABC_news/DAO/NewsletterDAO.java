package ABC_news.DAO;
import java.util.List;

public interface NewsletterDAO {
    boolean addSubscriber(String email);
    boolean exists(String email);
    List<String> getActiveEmails();
}
