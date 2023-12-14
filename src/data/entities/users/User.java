package data.entities.users;

import data.entities.pages.Page;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.UserType;

/**
 * The type User.
 */
@Setter
@Getter
public abstract class User {
    private String username = null;
    private int age = 0;
    private String city = null;
    private UserType userType = null;
    private Page currentPage = null;
    private boolean added = false;

    /**
     * Instantiates a new User.
     */
    protected User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    protected User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     * @param userType the user type
     */
    protected User(final String username, final int age, final String city,
                   final UserType userType) {
        this(username, age, city);
        this.userType = userType;
    }

    /**
     * returns true if the user is interacting with others and false otherwise
     *
     * @param timestamp the timestamp
     * @return true if the user is interacting with others and false otherwise
     */
    public abstract boolean interactingWithOthers(int timestamp);
}
