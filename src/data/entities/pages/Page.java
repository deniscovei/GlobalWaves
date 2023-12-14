package data.entities.pages;

import data.entities.users.User;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.PageType;

/**
 * The type Page.
 */
@Getter
@Setter
public abstract class Page {
    /**
     * The Creator.
     */
    protected User creator;
    /**
     * The Page type.
     */
    protected PageType pageType = null;

    /**
     * Instantiates a new Page.
     *
     * @param creator the creator
     */
    Page(final User creator) {
        this.creator = creator;
    }

    /**
     * returns the page's content in a formatted string
     *
     * @return the format
     */
    public abstract String getFormat();
}
