package data.entities.users;

import data.entities.Notification;
import data.entities.pages.Page;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.UserType;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
@Setter
@Getter
public abstract class User {
    protected String username = null;
    private int age = 0;
    private String city = null;
    private UserType userType = null;
    private Page currentPage = null;
    private boolean added = false;
    protected List<Notification> notifications = new ArrayList<>();

    public interface Tops {
        Tops clone();
    }

    protected Tops tops;

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

    public void clearNotifications() {
        getNotifications().clear();
    }

    /**
     * returns true if the user is interacting with others and false otherwise
     *
     * @param timestamp the timestamp
     * @return true if the user is interacting with others and false otherwise
     */
    public abstract boolean interactingWithOthers(int timestamp);
}
