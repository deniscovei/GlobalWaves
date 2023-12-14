package data.entities;

import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.SearchType;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Search bar.
 */
@Getter
@Setter
public class SearchBar {
    private SearchType searchType;
    private List<String> searchResults = new ArrayList<>();

    /**
     * flushes the search results
     */
    public void flush() {
        getSearchResults().clear();
    }
}
