package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.input.attributes.Filters;
import data.Database;
import data.entities.files.audioCollections.Album;
import data.entities.files.audioCollections.Playlist;
import data.entities.files.audioCollections.Podcast;
import data.entities.files.audioFiles.Song;
import commandmanager.output.Output;
import data.entities.users.contentCreator.Artist;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils;
import utils.AppUtils.SearchType;
import utils.AppUtils.UserType;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static utils.AppUtils.RES_COUNT_MAX;

/**
 * This class implements the command strategy for searching for audio files.
 */
public final class Search implements Command {
    @Override
    public Output action(final Input input) {
        Listener user = (Listener) Database.getInstance().findUser(input.getUsername());
        List<String> searchResults = Objects.requireNonNull(user).getSearchBar().getSearchResults();
        searchResults.clear();

        if (!Objects.requireNonNull(user).isOnline()) {
            return new Output.Builder()
                .command(input.getCommand())
                .timestamp(input.getTimestamp())
                .user(input.getUsername())
                .message(user.getUsername() + " is offline.")
                .results(searchResults)
                .build();
        }

        user.unloadAudioFile(input.getTimestamp());
        user.setPerformedSearch(true);

        switch (input.getType()) {
            case "song":
                user.getSearchBar().setSearchType(SearchType.SONG);
                List<Song> songs = Database.getInstance().getSongs();
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
                for (User currUser : Database.getInstance().getUsers()) {
                    if (currUser.getUserType().equals(UserType.ARTIST)) {
                        Artist artist = (Artist) currUser;
                        for (Album album : artist.getAlbums()) {
                            if (checkAlbumFilters(album, input.getFilters())) {
                                searchResults.add(album.getName());
                                if (searchResults.size() == RES_COUNT_MAX) {
                                    break;
                                }
                            }
                        }
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
                        if (checkArtistFilters(artist, input.getFilters())) {
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

        return new Output.Builder()
            .command(input.getCommand())
            .timestamp(input.getTimestamp())
            .user(input.getUsername())
            .message("Search returned " + searchResults.size() + " results")
            .results(searchResults)
            .build();
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
            return song.getName().toLowerCase().startsWith(searchedSong.toLowerCase());
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

    private boolean filterSongByTags(final Song song, final List<String> searchedTags) {
        if (!searchedTags.isEmpty()) {
            return new HashSet<>(song.getTags()).containsAll(searchedTags);
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
        if (playlist.getVisibility().equals(AppUtils.PRIVATE)
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

    private boolean checkArtistFilters(final User artist, final Filters filters) {
        return filters != null
            && filterArtistByName(artist, filters.getName());
    }

    private boolean filterArtistByName(final User artist, final String name) {
        if (name != null) {
            return artist.getUsername().startsWith(name);
        } else {
            return true;
        }
    }

    private boolean checkAlbumFilters(final Album album, final Filters filters) {
        return filters != null
            && filterAlbumByName(album, filters.getName())
            && filterAlbumByOwner(album, filters.getOwner())
            && filterAlbumByDescription(album, filters.getReleaseYear());
    }

    private boolean filterAlbumByName(final Album album, final String name) {
        if (name != null) {
            return album.getName().startsWith(name);
        } else {
            return true;
        }
    }

    private boolean filterAlbumByOwner(final Album album, final String owner) {
        if (owner != null) {
            return album.getOwner().startsWith(owner);
        } else {
            return true;
        }
    }

    private boolean filterAlbumByDescription(final Album album, final String description) {
        if (description != null) {
            return album.getDescription().startsWith(description);
        } else {
            return true;
        }
    }

    private boolean checkHostFilters(final User host, final Filters filters) {
        return filters != null
            && filterHostByName(host, filters.getName());
    }

    private boolean filterHostByName(final User host, final String name) {
        if (name != null) {
            return host.getUsername().startsWith(name);
        } else {
            return true;
        }
    }
}

