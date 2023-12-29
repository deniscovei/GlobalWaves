package fileio.output;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import data.entities.files.audioFiles.AudioFile;
import data.entities.users.Artist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AlbumOutput implements Serializable {
    private final String name;
    private final List<String> songs = new ArrayList<>();

    public AlbumOutput(final Artist artist, final int id) {
        this.name = artist.getAlbums().get(id).getName();

        List<AudioFile> albumSongs = artist.getAlbums().get(id).getAudioFiles();
        for (AudioFile song : albumSongs) {
            this.songs.add(song.getName());
        }
    }
}
