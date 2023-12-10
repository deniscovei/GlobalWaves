package data.entities.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Event {
    private String name = null;
    private String description = null;
    private String date = null;

    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }
}
