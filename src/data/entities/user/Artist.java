package data.entities.user;

import data.entities.audio.audioCollections.Album;
import lombok.Getter;
import lombok.Setter;
import utils.Constants.UserType;
import utils.Constants.Page;

import java.util.ArrayList;

@Getter
@Setter
public class Artist extends User {
    private ArrayList<Album> albums = new ArrayList<>();

    public Artist(String username, int age, String city) {
        super(username, age, city);
        setUserType(UserType.ARTIST);
        setCurrentPage(Page.ARTIST_PAGE);
    }

    public Album findAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    public void addAlbum(Album album) {
        getAlbums().add(album);
    }
}
