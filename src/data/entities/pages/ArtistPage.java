package data.entities.pages;

import data.entities.files.audioCollections.Album;
import data.entities.content.Event;
import data.entities.content.Merchandise;
import data.entities.users.contentCreator.Artist;
import data.entities.users.User;
import utils.AppUtils.PageType;

/**
 * The type Artist page.
 */
public final class ArtistPage extends Page {
    /**
     * Instantiates a new Artist page.
     *
     * @param creator the creator
     */
    public ArtistPage(final User creator) {
        super(creator);
        pageType = PageType.ARTIST_PAGE;
    }

    @Override
    public String getFormat() {
        Artist artist = (Artist) getCreator();
        StringBuilder result = new StringBuilder("Albums:\n\t[");

        for (Album album : artist.getAlbums()) {
            result.append(album.getName());
            if (album != artist.getAlbums().get(artist.getAlbums().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nMerch:\n\t[");

        for (Merchandise merchandise : artist.getMerchandise()) {
            result.append(merchandise.getName()).append(" - ").append(merchandise.getPrice())
                    .append(":\n\t").append(merchandise.getDescription());
            if (merchandise != artist.getMerchandise().get(artist.getMerchandise().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nEvents:\n\t[");

        for (Event event : artist.getEvents()) {
            result.append(event.getName()).append(" - ").append(event.getDate())
                    .append(":\n\t").append(event.getDescription());
            if (event != artist.getEvents().get(artist.getEvents().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]");

        return result.toString();
    }
}
