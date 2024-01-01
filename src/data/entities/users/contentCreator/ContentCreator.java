package data.entities.users.contentCreator;

import data.entities.users.Listener;
import data.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ContentCreator extends User {
    List<Listener> subscribers = new ArrayList<>();

    public ContentCreator() {
    }

    public ContentCreator(final String username, final int age, final String city) {
        super(username, age, city);
    }

    public void registerSubscribe(Listener listener) {
        getSubscribers().add(listener);
    }

    public void registerUnsubscribe(Listener listener) {
        getSubscribers().remove(listener);
    }
}
