package commandManager.input.commandStrategies;

import commandManager.input.Input;
import commandManager.input.attributes.Filters;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioCollections.Podcast;
import data.entities.audio.audioFiles.Song;
import commandManager.output.Output;
import data.entities.user.User;
import utils.Constants;

import java.util.ArrayList;

import static utils.Constants.RES_COUNT_MAX;

public final class SearchStrategy implements CommandStrategy {
    @Override
    public Output action(Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        ArrayList<File> searchResults = user.getSearchResults();

        user.unloadAudioFile(input.getTimestamp());
        searchResults.clear();
        user.setPerformedSearch(true);

        switch (input.getType()) {
            case "song":
                ArrayList<Song> songs = Database.getInstance().getSongs();
                for (Song song : songs) {
                    if (checkSongFilters(song, input.getFilters())) {
                        searchResults.add(song);
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "podcast":
                for (Podcast podcast : Database.getInstance().getPodcasts()) {
                    if (checkPodcastFilters(podcast, input.getFilters())) {
                        searchResults.add(podcast);
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "playlist":
                for (Playlist playlist : Database.getInstance().getPlaylists()) {
                    if (checkPlaylistFilters(playlist, input.getFilters(), input.getUsername())) {
                        searchResults.add(playlist);
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
        }

        return new Output(input, "Search returned " + searchResults.size() + " results",
                          AudioFile.getFileNames(searchResults));
    }

    private boolean checkSongFilters(Song song, Filters filters) {
        return filters != null &&
                filterSongByName(song, filters.getName()) &&
                filterSongByAlbum(song, filters.getAlbum()) &&
                filterSongByTags(song, filters.getTags()) &&
                filterSongByLyrics(song, filters.getLyrics()) &&
                filterSongByGenre(song, filters.getGenre()) &&
                filterSongByReleaseYear(song, filters.getReleaseYear()) &&
                filterSongByArtist(song, filters.getArtist());
    }

    private boolean filterSongByName(Song song, String searchedSong) {
        if (searchedSong != null) {
            return song.getName().startsWith(searchedSong);
        } else {
            return true;
        }
    }

    private boolean filterSongByAlbum(Song song, String searchedAlbum) {
        if (searchedAlbum != null) {
            return song.getAlbum().equals(searchedAlbum);
        } else {
            return true;
        }
    }

    private boolean filterSongByTags(Song song, ArrayList<String> searchedTags) {
        if (!searchedTags.isEmpty()) {
            return song.getTags().containsAll(searchedTags);
        } else {
            return true;
        }
    }

    private boolean filterSongByLyrics(Song song, String searchedLyrics) {
        if (searchedLyrics != null) {
            return song.getLyrics().toLowerCase().contains(searchedLyrics.toLowerCase());
        } else {
            return true;
        }
    }

    private boolean filterSongByGenre(Song song, String searchedGenre) {
        if (searchedGenre != null) {
            return song.getGenre().equalsIgnoreCase(searchedGenre);
        } else {
            return true;
        }
    }

    private boolean filterSongByReleaseYear(Song song, String searchedReleaseYear) {
        if (searchedReleaseYear != null) {
            int comparator = searchedReleaseYear.charAt(0) == '>' ? 1 : -1;
            int searchedYear = Integer.parseInt(searchedReleaseYear.substring(1));
            return comparator * song.getReleaseYear() > comparator * searchedYear;
        } else {
            return true;
        }
    }

    private boolean filterSongByArtist(Song song, String searchedArtist) {
        if (searchedArtist != null) {
            return song.getArtist().equals(searchedArtist);
        } else {
            return true;
        }
    }

    private boolean checkPodcastFilters(Podcast podcast, Filters filters) {
        return filters != null &&
                filterPodcastByName(podcast, filters.getName()) &&
                filterPodcastByOwner(podcast, filters.getOwner());
    }

    private boolean filterPodcastByName(Podcast podcast, String searchedPodcast) {
        if (searchedPodcast != null) {
            return podcast.getName().startsWith(searchedPodcast);
        } else {
            return true;
        }
    }

    private boolean filterPodcastByOwner(Podcast podcast, String searchedOwner) {
        if (searchedOwner != null) {
            return podcast.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }

    private boolean checkPlaylistFilters(Playlist playlist, Filters filters, String currentUser) {
        if (playlist.getVisibility().equals(Constants.PRIVATE) &&
            !playlist.getOwner().equals(currentUser)) {
            return false;
        }
        return filters != null &&
                filterPlaylistByName(playlist, filters.getName()) &&
                filterPlaylistByOwner(playlist, filters.getOwner());
    }

    private boolean filterPlaylistByName(Playlist playlist, String searchedPlaylist) {
        if (searchedPlaylist != null) {
            return playlist.getName().startsWith(searchedPlaylist);
        } else {
            return true;
        }
    }

    private boolean filterPlaylistByOwner(Playlist playlist, String searchedOwner) {
        if (searchedOwner != null) {
            return playlist.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }
}

