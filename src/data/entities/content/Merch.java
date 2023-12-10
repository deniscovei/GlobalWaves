package data.entities.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Merch {
    private String name = null;
    private String description = null;
    private int price = 0;

    public Merch(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
