package CommandTypes.input;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import CommandTypes.output.OutputCommand;
import lombok.Getter;

import java.util.ArrayList;

public final class Search {
    private static final int resCountMax = 5;
    @Getter
    private static ArrayList<String> results = new ArrayList<>();


    public static void setResults(ArrayList<String> results) {
        Search.results.addAll(results);
    }

    public static OutputCommand action(InputCommand inputCommand, LibraryInput library) {
        results.clear();
        OutputCommand outputCommand = new OutputCommand(inputCommand.getCommand(), inputCommand.getUsername(),
                inputCommand.getTimestamp(), true, true);

        switch (inputCommand.getType()) {
            case "song":
                for (SongInput song : library.getSongs()) {
                    if (checkSongFilters(song, inputCommand.getFilters(), inputCommand)) {
                        results.add(song.getName());
                        if (results.size() == resCountMax) {
                            break;
                        }
                    }
                }
                break;
            case "podcast":
                for (PodcastInput podcast : library.getPodcasts()) {
                    if (checkPodcastFilters(podcast, inputCommand.getFilters(), inputCommand)) {
                        results.add(podcast.getName());
                        if (results.size() == resCountMax) {
                            break;
                        }
                    }
                }
                break;
        }

        outputCommand.setMessage("Search returned " + results.size() + " results");
        outputCommand.setResults(results);
        return outputCommand;
    }

    private static boolean checkSongFilters(SongInput song, Filter filters, InputCommand inputCommand) {
        return filterSongByName(song, filters.getName(), inputCommand) &&
                filterSongByAlbum(song, filters.getAlbum(), inputCommand) &&
                filterSongByTags(song, filters.getTags(), inputCommand) &&
                filterSongByLyrics(song, filters.getLyrics(), inputCommand) &&
                filterSongByGenre(song, filters.getGenre(), inputCommand) &&
                filterSongByReleaseYear(song, filters.getReleaseYear(), inputCommand) &&
                filterSongByArtist(song, filters.getArtist(), inputCommand);
    }

    private static boolean filterSongByName(SongInput song, String searchedSong, InputCommand inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return song.getName().startsWith(searchedSong);
        } else {
            return true;
        }
    }

    private static boolean filterSongByAlbum(SongInput song, String searchedAlbum, InputCommand inputCommand) {
        if (inputCommand.getFilters().getAlbum() != null) {
            return song.getAlbum().equals(searchedAlbum);
        } else {
            return true;
        }
    }

    private static boolean filterSongByTags(SongInput song, ArrayList<String> searchedTags, InputCommand inputCommand) {
        if (!inputCommand.getFilters().getTags().isEmpty()) {
            return song.getTags().containsAll(searchedTags);
        } else {
            return true;
        }
    }

    private static boolean filterSongByLyrics(SongInput song, String searchedLyrics, InputCommand inputCommand) {
        if (inputCommand.getFilters().getLyrics() != null) {
            return song.getLyrics().contains(searchedLyrics);
        } else {
            return true;
        }
    }

    private static boolean filterSongByGenre(SongInput song, String searchedGenre, InputCommand inputCommand) {
        if (inputCommand.getFilters().getGenre() != null) {
            return song.getGenre().toLowerCase().equals(searchedGenre);
        } else {
            return true;
        }
    }

    private static boolean filterSongByReleaseYear(SongInput song, String searchedReleaseYear, InputCommand inputCommand) {
        if (inputCommand.getFilters().getReleaseYear() != null) {
            int comparator = searchedReleaseYear.charAt(0) == '>' ? 1 : -1;
            int searchedYear = Integer.parseInt(searchedReleaseYear.substring(1));
            return comparator * song.getReleaseYear() > comparator * searchedYear;
        } else {
            return true;
        }
    }

    private static boolean filterSongByArtist(SongInput song, String searchedArtist, InputCommand inputCommand) {
        if (inputCommand.getFilters().getArtist() != null) {
            return song.getArtist().equals(searchedArtist);
        } else {
            return true;
        }
    }

    private static boolean checkPodcastFilters(PodcastInput podcast, Filter filters, InputCommand inputCommand) {
        return filterPodcastByName(podcast, filters.getName(), inputCommand) &&
                filterPodcastByOwner(podcast, filters.getOwner(), inputCommand);
    }

    private static boolean filterPodcastByName(PodcastInput podcast, String searchedPodcast, InputCommand inputCommand) {
        if (inputCommand.getFilters().getName() != null) {
            return podcast.getName().startsWith(searchedPodcast);
        } else {
            return true;
        }
    }

    private static boolean filterPodcastByOwner(PodcastInput podcast, String searchedOwner, InputCommand inputCommand) {
        if (inputCommand.getFilters().getOwner() != null) {
            return podcast.getOwner().equals(searchedOwner);
        } else {
            return true;
        }
    }
}

