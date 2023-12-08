package data.entities.user;

import lombok.Getter;
import lombok.Setter;
import utils.Constants.UserType;
import utils.Constants.Page;

@Setter
@Getter
public class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private UserType userType = null;
    private Page currentPage = null;
    private boolean added = false;

    public User() {
    }

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    public User(final String username, final int age, final String city, final UserType userType) {
        this(username, age, city);
        this.userType = userType;
    }
}
