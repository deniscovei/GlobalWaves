package commandManager.input.commands;

import commandManager.input.Input;
import commandManager.input.attributes.Filters;
import data.Database;
import data.entities.audio.audioCollections.Album;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioCollections.Podcast;
import data.entities.audio.audioFiles.Song;
import commandManager.output.Output;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.Extras;
import utils.Extras.SearchType;
import utils.Extras.UserType;

import java.util.ArrayList;
import java.util.Objects;

import static utils.Extras.RES_COUNT_MAX;

/**
 * This class implements the command strategy for searching for audio files.
 */
public final class Search implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        ArrayList<String> searchResults = Objects.requireNonNull(user).getSearchBar().getSearchResults();
        searchResults.clear();

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output(input, user.getUsername() + " is offline.", searchResults);
        }

        user.unloadAudioFile(input.getTimestamp());
        user.setPerformedSearch(true);

        switch (input.getType()) {
            case "song":
                user.getSearchBar().setSearchType(SearchType.SONG);
                ArrayList<Song> songs = Database.getInstance().getSongs();
                for (Song song : songs) {
                    if (checkSongFilters(song, input.getFilters())) {
                        searchResults.add(song.getName());
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "podcast":
                user.getSearchBar().setSearchType(SearchType.PODCAST);
                for (Podcast podcast : Database.getInstance().getPodcasts()) {
                    if (checkPodcastFilters(podcast, input.getFilters())) {
                        searchResults.add(podcast.getName());
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "playlist":
                user.getSearchBar().setSearchType(SearchType.PLAYLIST);
                for (Playlist playlist : Database.getInstance().getPlaylists()) {
                    if (checkPlaylistFilters(playlist, input.getFilters(), input.getUsername())) {
                        searchResults.add(playlist.getName());
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "album":
                user.getSearchBar().setSearchType(SearchType.ALBUM);
                for (Album album : Database.getInstance().getAlbums()) {
                    if (checkAlbumFilters(album, input.getFilters())) {
                        searchResults.add(album.getName());
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "artist":
                user.getSearchBar().setSearchType(SearchType.ARTIST);
                for (User artist : Database.getInstance().getUsers()) {
                    if (artist.getUserType().equals(UserType.ARTIST)) {
                        if (checkArtistFilters(artist, input.getFilters(), input.getUsername())) {
                            searchResults.add(artist.getUsername());
                            if (searchResults.size() == RES_COUNT_MAX) {
                                break;
                            }
                        }
                    }
                }
                break;
            case "host":
                user.getSearchBar().setSearchType(SearchType.HOST);
                for (User host : Database.getInstance().getUsers()) {
                    if (host.getUserType().equals(UserType.HOST)) {
                        if (checkHostFilters(host, input.getFilters())) {
                            searchResults.add(host.getUsername());
                            if (searchResults.size() == RES_COUNT_MAX) {
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }

        return new Output(input, "Search returned " + searchResults.size() + " results", searchResults);
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
        if (playlist.getVisibility().equals(Extras.PRIVATE)
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

    private boolean checkArtistFilters(User artist, Filters filters, String username) {
        return filters != null
                && filterArtistByName(artist, filters.getName());
    }

    private boolean filterArtistByName(User artist, String name) {
        if (name != null) {
            return artist.getUsername().startsWith(name);
        } else {
            return true;
        }
    }

    private boolean checkAlbumFilters(Album album, Filters filters) {
        return filters != null
                && filterAlbumByName(album, filters.getName())
                && filterAlbumByOwner(album, filters.getOwner())
                && filterAlbumByDescription(album, filters.getReleaseYear());
    }

    private boolean filterAlbumByName(Album album, String name) {
        if (name != null) {
            return album.getName().startsWith(name);
        } else {
            return true;
        }
    }

    private boolean filterAlbumByOwner(Album album, String owner) {
        if (owner != null) {
            return album.getOwner().startsWith(owner);
        } else {
            return true;
        }
    }

    private boolean filterAlbumByDescription(Album album, String description) {
        if (description != null) {
            return album.getDescription().startsWith(description);
        } else {
            return true;
        }
    }

    private boolean checkHostFilters(User host, Filters filters) {
        return filters != null
                && filterHostByName(host, filters.getName());
    }

    private boolean filterHostByName(User host, String name) {
        if (name != null) {
            return host.getUsername().startsWith(name);
        } else {
            return true;
        }
    }
}

