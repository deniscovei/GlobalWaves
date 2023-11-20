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
                    if (checkSongFilters(song, input.getFilters(), input)) {
                        searchResults.add(song);
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "podcast":
                for (Podcast podcast : Database.getInstance().getPodcasts()) {
                    if (checkPodcastFilters(podcast, input.getFilters(), input)) {
                        searchResults.add(podcast);
                        if (searchResults.size() == RES_COUNT_MAX) {
                            break;
                        }
                    }
                }
                break;
            case "playlist":
                for (Playlist playlist : Database.getInstance().getPlaylists()) {
                    if (checkPlaylistFilters(playlist, input.getFilters(), input)) {
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

    private static boolean checkSongFilters(Song song, Filters filters, Input inputCommand) {
        return filterSongByName(song, filters.getName(), inputCommand) &&
                filterSongByAlbum(song, filters.getAlbum(), inputCommand) &&
                filterSongByTags(song, filters.getTags(), inputCommand) &&
                filterSongByLyrics(song, filters.getLyrics(), inputCommand) &&
                filterSongByGenre(song, filters.getGenre(), inputCommand) &&
                filterSongByReleaseYear(song, filters.getReleaseYear(), inputCommand) &&
                filterSongByArtist(song, filters.getArtist(), inputCommand);
    }

    private static boolean filterSongByName(Song song, String searchedSong, Input inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return song.getName().startsWith(searchedSong);
        } else {
            return true;
        }
    }

    private static boolean filterSongByAlbum(Song song, String searchedAlbum, Input inputCommand) {
        if (inputCommand.getFilters().getAlbum() != null) {
            return song.getAlbum().equals(searchedAlbum);
        } else {
            return true;
        }
    }

    private static boolean filterSongByTags(Song song, ArrayList<String> searchedTags, Input inputCommand) {
        if (!inputCommand.getFilters().getTags().isEmpty()) {
            return song.getTags().containsAll(searchedTags);
        } else {
            return true;
        }
    }

    private static boolean filterSongByLyrics(Song song, String searchedLyrics, Input inputCommand) {
        if (inputCommand.getFilters().getLyrics() != null) {
            return song.getLyrics().toLowerCase().contains(searchedLyrics.toLowerCase());
        } else {
            return true;
        }
    }

    private static boolean filterSongByGenre(Song song, String searchedGenre, Input inputCommand) {
        if (inputCommand.getFilters().getGenre() != null) {
            return song.getGenre().equalsIgnoreCase(searchedGenre);
        } else {
            return true;
        }
    }

    private static boolean filterSongByReleaseYear(Song song, String searchedReleaseYear, Input inputCommand) {
        if (inputCommand.getFilters().getReleaseYear() != null) {
            int comparator = searchedReleaseYear.charAt(0) == '>' ? 1 : -1;
            int searchedYear = Integer.parseInt(searchedReleaseYear.substring(1));
            return comparator * song.getReleaseYear() > comparator * searchedYear;
        } else {
            return true;
        }
    }

    private static boolean filterSongByArtist(Song song, String searchedArtist, Input inputCommand) {
        if (inputCommand.getFilters().getArtist() != null) {
            return song.getArtist().equals(searchedArtist);
        } else {
            return true;
        }
    }

    private static boolean checkPodcastFilters(Podcast podcast, Filters filters, Input inputCommand) {
        return filterPodcastByName(podcast, filters.getName(), inputCommand) &&
                filterPodcastByOwner(podcast, filters.getOwner(), inputCommand);
    }

    private static boolean filterPodcastByName(Podcast podcast, String searchedPodcast, Input inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return podcast.getName().startsWith(searchedPodcast);
        } else {
            return true;
        }
    }

    private static boolean filterPodcastByOwner(Podcast podcast, String searchedOwner, Input inputCommand) {
        if (inputCommand.getFilters().getOwner() != null) {
            return podcast.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }

    private static boolean checkPlaylistFilters(Playlist playlist, Filters filters, Input inputCommand) {
        if (playlist.getVisibility().equals("private")) {
            return false;
        }
        return filterPlaylistByName(playlist, filters.getName(), inputCommand) &&
                filterPlaylistByOwner(playlist, filters.getOwner(), inputCommand);
    }

    private static boolean filterPlaylistByName(Playlist playlist, String searchedPlaylist, Input inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return playlist.getName().startsWith(searchedPlaylist);
        } else {
            return true;
        }
    }

    private static boolean filterPlaylistByOwner(Playlist playlist, String searchedOwner, Input inputCommand) {
        if (inputCommand.getFilters().getOwner() != null) {
            return playlist.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }
}

