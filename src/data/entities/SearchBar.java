package data.entities;

import lombok.Getter;
import lombok.Setter;
import utils.Extras.SearchType;

import java.util.ArrayList;

@Getter
@Setter
public class SearchBar {
    private SearchType searchType;
    private ArrayList<String> searchResults = new ArrayList<>();

    public void flush() {
        searchResults = new ArrayList<>();
    }
}
