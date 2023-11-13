package commands.derived.input.commandTypes;

import commands.derived.input.inputCommand.InputCommand;
import commands.derived.input.attributes.Filters;
import data.database.Database;
import data.entities.audio.base.AudioFile;
import data.entities.audio.derived.Podcast;
import data.entities.audio.derived.Song;
import commands.derived.output.OutputCommand;

import java.util.ArrayList;

public final class Search {
    private static final int resCountMax = 5;

    public static OutputCommand action(InputCommand inputCommand) {
        ArrayList<AudioFile> searchResults = Database.getInstance().findUser(inputCommand.getUsername()).getSearchResults();
        searchResults.clear();

        switch (inputCommand.getType()) {
            case "song":
                ArrayList <Song> songs = Database.getInstance().getSongs();
                for (Song song : songs) {
                    if (checkSongFilters(song, inputCommand.getFilters(), inputCommand)) {
                        searchResults.add(song);
                        if (searchResults.size() == resCountMax) {
                            break;
                        }
                    }
                }
                break;
            case "podcast":
                for (Podcast podcast : Database.getInstance().getPodcasts()) {
                    if (checkPodcastFilters(podcast, inputCommand.getFilters(), inputCommand)) {
                        searchResults.add(podcast);
                        if (searchResults.size() == resCountMax) {
                            break;
                        }
                    }
                }
                break;
        }

        return new OutputCommand(inputCommand, "Search returned " + searchResults.size() + " results",
                                 AudioFile.getNameList(searchResults));
    }

    private static boolean checkSongFilters(Song song, Filters filters, InputCommand inputCommand) {
        return filterSongByName(song, filters.getName(), inputCommand) &&
                filterSongByAlbum(song, filters.getAlbum(), inputCommand) &&
                filterSongByTags(song, filters.getTags(), inputCommand) &&
                filterSongByLyrics(song, filters.getLyrics(), inputCommand) &&
                filterSongByGenre(song, filters.getGenre(), inputCommand) &&
                filterSongByReleaseYear(song, filters.getReleaseYear(), inputCommand) &&
                filterSongByArtist(song, filters.getArtist(), inputCommand);
    }

    private static boolean filterSongByName(Song song, String searchedSong, InputCommand inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return song.getName().startsWith(searchedSong);
        } else {
            return true;
        }
    }

    private static boolean filterSongByAlbum(Song song, String searchedAlbum, InputCommand inputCommand) {
        if (inputCommand.getFilters().getAlbum() != null) {
            return song.getAlbum().equals(searchedAlbum);
        } else {
            return true;
        }
    }

    private static boolean filterSongByTags(Song song, ArrayList<String> searchedTags, InputCommand inputCommand) {
        if (!inputCommand.getFilters().getTags().isEmpty()) {
            return song.getTags().containsAll(searchedTags);
        } else {
            return true;
        }
    }

    private static boolean filterSongByLyrics(Song song, String searchedLyrics, InputCommand inputCommand) {
        if (inputCommand.getFilters().getLyrics() != null) {
            return song.getLyrics().contains(searchedLyrics);
        } else {
            return true;
        }
    }

    private static boolean filterSongByGenre(Song song, String searchedGenre, InputCommand inputCommand) {
        if (inputCommand.getFilters().getGenre() != null) {
            return song.getGenre().toLowerCase().equals(searchedGenre);
        } else {
            return true;
        }
    }

    private static boolean filterSongByReleaseYear(Song song, String searchedReleaseYear, InputCommand inputCommand) {
        if (inputCommand.getFilters().getReleaseYear() != null) {
            int comparator = searchedReleaseYear.charAt(0) == '>' ? 1 : -1;
            int searchedYear = Integer.parseInt(searchedReleaseYear.substring(1));
            return comparator * song.getReleaseYear() > comparator * searchedYear;
        } else {
            return true;
        }
    }

    private static boolean filterSongByArtist(Song song, String searchedArtist, InputCommand inputCommand) {
        if (inputCommand.getFilters().getArtist() != null) {
            return song.getArtist().equals(searchedArtist);
        } else {
            return true;
        }
    }

    private static boolean checkPodcastFilters(Podcast podcast, Filters filters, InputCommand inputCommand) {
        return filterPodcastByName(podcast, filters.getName(), inputCommand) &&
                filterPodcastByOwner(podcast, filters.getOwner(), inputCommand);
    }

    private static boolean filterPodcastByName(Podcast podcast, String searchedPodcast, InputCommand inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return podcast.getName().startsWith(searchedPodcast);
        } else {
            return true;
        }
    }

    private static boolean filterPodcastByOwner(Podcast podcast, String searchedOwner, InputCommand inputCommand) {
        if (inputCommand.getFilters().getOwner() != null) {
            return podcast.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }
}

