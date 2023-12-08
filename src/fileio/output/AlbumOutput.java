package fileio.output;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.user.Artist;

import java.io.Serializable;
import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AlbumOutput implements Serializable {
    String name = null;
    ArrayList<String> songs = new ArrayList<>();

    public AlbumOutput() {
    }

    public AlbumOutput(Artist artist, int id) {
        this.name = artist.getAlbums().get(id).getName();

        ArrayList<AudioFile> songs = artist.getAlbums().get(id).getAudioFiles();
        for (AudioFile song : songs) {
            this.songs.add(song.getName());
        }
    }
}