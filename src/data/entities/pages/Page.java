package data.entities.pages;

import data.entities.users.User;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.PageType;

@Getter
@Setter
public abstract class Page {
    protected User creator = null;
    protected PageType pageType = null;

    Page(final User creator) {
        this.creator = creator;
    }

    /**
     * returns the page's content in a formatted string
     */
    public abstract String getFormat();
}
