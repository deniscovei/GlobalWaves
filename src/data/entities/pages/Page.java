package data.entities.pages;

import data.entities.users.User;
import lombok.Getter;
import lombok.Setter;
import utils.Extras.PageType;

@Getter
@Setter
public class Page {
    protected User viewer = null;
    protected PageType pageType = null;

    Page(User viewer) {
        this.viewer = viewer;
    }

    public String getFormat() {
        return null;
    }
}
