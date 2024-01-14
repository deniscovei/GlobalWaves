package data.entities.files.audioFiles;

import data.Database;
import data.entities.users.Listener;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils.FileType;

@Getter
@Setter
public final class Ad extends Song {
    private int price;

    public Ad(final int price) {
        Song ad = Database.getInstance().getAd();
        this.fileType = FileType.AD;
        this.name = ad.getName();
        this.duration = ad.getDuration();
        this.album = ad.getAlbum();
        this.tags.addAll(ad.getTags());
        this.lyrics = ad.getLyrics();
        this.genre = ad.getGenre();
        this.releaseYear = ad.getReleaseYear();
        this.artist = ad.getArtist();
        this.price = price;
    }

    @Override
    public void listen(final Listener listener) {
        listener.getHistory().add(this);
    }
}
