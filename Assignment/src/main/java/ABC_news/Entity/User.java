package ABC_news.Entity;

import java.time.LocalDate;

public class User {
    private String id;
    private String password;
    private String fullname;
    private LocalDate birthday;
    private boolean gender;
    private String mobile;
    private String email;
    private boolean role;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public boolean isGender() { return gender; }
    public void setGender(boolean gender) { this.gender = gender; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isRole() { return role; }
    public void setRole(boolean role) { this.role = role; }
}
