package data.entities.pages;

import data.entities.users.User;
import lombok.Getter;
import lombok.Setter;
import utils.Extras.PageType;

@Getter
@Setter
public class Page {
    protected User creator = null;
    protected PageType pageType = null;

    Page(User creator) {
        this.creator = creator;
    }

    public String getFormat() {
        return null;
    }
}
