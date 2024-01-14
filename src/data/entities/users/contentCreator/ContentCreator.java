package data.entities.users.contentCreator;

import data.entities.users.Listener;
import data.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Content creator.
 */
@Getter
@Setter
public abstract class ContentCreator extends User {
    private List<Listener> subscribers = new ArrayList<>();

    /**
     * Instantiates a new Content creator.
     */
    public ContentCreator() {
    }

    /**
     * Instantiates a new Content creator.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public ContentCreator(final String username, final int age, final String city) {
        super(username, age, city);
    }

    /**
     * Register subscribe.
     *
     * @param listener the listener
     */
    public void registerSubscribe(final Listener listener) {
        getSubscribers().add(listener);
    }

    /**
     * Register unsubscribe.
     *
     * @param listener the listener
     */
    public void registerUnsubscribe(final Listener listener) {
        getSubscribers().remove(listener);
    }
}
