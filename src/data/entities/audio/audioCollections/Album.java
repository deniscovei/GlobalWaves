package data.entities.audio.audioCollections;

import data.entities.audio.audioFiles.Song;
import fileio.input.SongInput;

import java.util.ArrayList;

public class Album extends AudioCollection {
    int releaseYear = 0;
    String description = null;

    public Album(final String name, final String owner, final int releaseYear, final ArrayList<SongInput> songs) {
        super(name, owner);
        this.releaseYear = releaseYear;
        for (SongInput songInput : songs) {
            audioFiles.add(new Song(songInput));
        }
    }
}
