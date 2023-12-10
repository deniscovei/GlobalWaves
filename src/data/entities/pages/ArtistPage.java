package data.entities.pages;

import data.entities.audio.audioCollections.Album;
import data.entities.content.Event;
import data.entities.content.Merch;
import data.entities.users.Artist;
import data.entities.users.User;
import utils.Extras.PageType;

public final class ArtistPage extends Page {
    public ArtistPage(User viewer) {
        super(viewer);
        pageType = PageType.ARTIST_PAGE;
    }

    @Override
    public String getFormat() {
        Artist artist = (Artist) getViewer();
        StringBuilder result = new StringBuilder("Albums:\n\t[");

        for (Album album : artist.getAlbums()) {
            result.append(album.getName());
            if (album != artist.getAlbums().get(artist.getAlbums().size() - 1)) {
                result.append(",");
            }
        }

        result.append("]\n\nMerch:\n\t[");

        for (Merch merch : artist.getMerches()) {
            result.append(merch.getName()).append(" - ").append(merch.getPrice())
                    .append(":\n\t").append(merch.getDescription());
            if (merch != artist.getMerches().get(artist.getMerches().size() - 1)) {
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
