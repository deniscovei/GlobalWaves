package data.entities.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Merchandise {
    private String name;
    private String description;
    private int price;

    public Merchandise(final String name, final String description, final int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
