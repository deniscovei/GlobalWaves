package data.entities;

import data.Database;
import data.entities.audio.File;
import data.entities.pages.Page;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.SelectionType;
import utils.AppUtils.SearchType;

import java.util.Objects;

/**
 * The type Selection.
 */
@Getter
@Setter
public class Selection {
    private SelectionType selectionType = SelectionType.FILE;
    private File selectedFile = null;
    private Page selectedPage = null;

    /**
     * Instantiates a new Selection.
     *
     * @param searchType the search type
     * @param selection  the selection
     */
// selectionType is FILE by default
    public Selection(final SearchType searchType, final String selection) {
        switch (searchType) {
            case SONG:
                this.selectedFile = Database.getInstance().findSong(selection);
                break;
            case PLAYLIST:
                this.selectedFile = Database.getInstance().findPlaylist(selection);
                break;
            case PODCAST:
                this.selectedFile = Database.getInstance().findPodcast(selection);
                break;
            case ALBUM:
                this.selectedFile = Database.getInstance().findAlbum(selection);
                break;
            case ARTIST, HOST:
                this.selectionType = SelectionType.PAGE;
                this.selectedPage = Objects.requireNonNull(Database.getInstance()
                    .findUser(selection)).getCurrentPage();
                break;
            default:
                break;
        }
    }
}
