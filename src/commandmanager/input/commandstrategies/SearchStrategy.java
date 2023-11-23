package commandmanager.input.commandstrategies;

import commandmanager.input.Input;
import commandmanager.input.attributes.Filters;
import data.Database;
import data.entities.audio.File;
import data.entities.audio.audiofiles.AudioFile;
import data.entities.audio.audiocollections.Playlist;
import data.entities.audio.audiocollections.Podcast;
import data.entities.audio.audiofiles.Song;
import commandmanager.output.Output;
import data.entities.user.User;
import utils.Constants;

import java.util.ArrayList;
import java.util.Objects;

import static utils.Constants.RES_COUNT_MAX;

/**
 * This class implements the command strategy for searching for audio files.
 */
public final class SearchStrategy implements CommandStrategy {
    @Override
    public Output action(final Input input) {
        User user = Database.getInstance().findUser(input.getUsername());
        ArrayList<File> searchResults = Objects.requireNonNull(user).getSearchResults();

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
            default:
                break;
        }

        return new Output(input, "Search returned " + searchResults.size() + " results",
                AudioFile.getFileNames(searchResults));
    }

    private boolean checkSongFilters(final Song song, final Filters filters) {
        return filters != null
                && filterSongByName(song, filters.getName())
                && filterSongByAlbum(song, filters.getAlbum())
                && filterSongByTags(song, filters.getTags())
                && filterSongByLyrics(song, filters.getLyrics())
                && filterSongByGenre(song, filters.getGenre())
                && filterSongByReleaseYear(song, filters.getReleaseYear())
                && filterSongByArtist(song, filters.getArtist());
    }

    private boolean filterSongByName(final Song song, final String searchedSong) {
        if (searchedSong != null) {
            return song.getName().startsWith(searchedSong);
        } else {
            return true;
        }
    }

    private boolean filterSongByAlbum(final Song song, final String searchedAlbum) {
        if (searchedAlbum != null) {
            return song.getAlbum().equals(searchedAlbum);
        } else {
            return true;
        }
    }

    private boolean filterSongByTags(final Song song, final ArrayList<String> searchedTags) {
        if (!searchedTags.isEmpty()) {
            return song.getTags().containsAll(searchedTags);
        } else {
            return true;
        }
    }

    private boolean filterSongByLyrics(final Song song, final String searchedLyrics) {
        if (searchedLyrics != null) {
            return song.getLyrics().toLowerCase().contains(searchedLyrics.toLowerCase());
        } else {
            return true;
        }
    }

    private boolean filterSongByGenre(final Song song, final String searchedGenre) {
        if (searchedGenre != null) {
            return song.getGenre().equalsIgnoreCase(searchedGenre);
        } else {
            return true;
        }
    }

    private boolean filterSongByReleaseYear(final Song song, final String searchedReleaseYear) {
        if (searchedReleaseYear != null) {
            int comparator = searchedReleaseYear.charAt(0) == '>' ? 1 : -1;
            int searchedYear = Integer.parseInt(searchedReleaseYear.substring(1));
            return comparator * song.getReleaseYear() > comparator * searchedYear;
        } else {
            return true;
        }
    }

    private boolean filterSongByArtist(final Song song, final String searchedArtist) {
        if (searchedArtist != null) {
            return song.getArtist().equals(searchedArtist);
        } else {
            return true;
        }
    }

    private boolean checkPodcastFilters(final Podcast podcast, final Filters filters) {
        return filters != null
                && filterPodcastByName(podcast, filters.getName())
                && filterPodcastByOwner(podcast, filters.getOwner());
    }

    private boolean filterPodcastByName(final Podcast podcast, final String searchedPodcast) {
        if (searchedPodcast != null) {
            return podcast.getName().startsWith(searchedPodcast);
        } else {
            return true;
        }
    }

    private boolean filterPodcastByOwner(final Podcast podcast, final String searchedOwner) {
        if (searchedOwner != null) {
            return podcast.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }

    private boolean checkPlaylistFilters(final Playlist playlist, final Filters filters,
                                         final String currentUser) {
        if (playlist.getVisibility().equals(Constants.PRIVATE)
                && !playlist.getOwner().equals(currentUser)) {
            return false;
        }
        return filters != null
                && filterPlaylistByName(playlist, filters.getName())
                && filterPlaylistByOwner(playlist, filters.getOwner());
    }

    private boolean filterPlaylistByName(final Playlist playlist, final String searchedPlaylist) {
        if (searchedPlaylist != null) {
            return playlist.getName().startsWith(searchedPlaylist);
        } else {
            return true;
        }
    }

    private boolean filterPlaylistByOwner(final Playlist playlist, final String searchedOwner) {
        if (searchedOwner != null) {
            return playlist.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }
}

