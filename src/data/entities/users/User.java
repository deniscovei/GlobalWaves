package data.entities.users;

import data.entities.pages.Page;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.UserType;

@Setter
@Getter
public abstract class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private UserType userType = null;
    private Page currentPage = null;
    private boolean added = false;

    protected User() {
    }

    protected User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    protected User(final String username, final int age, final String city,
                   final UserType userType) {
        this(username, age, city);
        this.userType = userType;
    }

    /**
     * returns true if the user is interacting with others and false otherwise
     */
    public abstract boolean interactingWithOthers(int timestamp);
}
