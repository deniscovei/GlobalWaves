package utils;

import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioFiles.AudioFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public final class Extras {
    public static final int RES_COUNT_MAX = 5;

    public enum FileType {
        SONG,
        EPISODE,
        PODCAST,
        PLAYLIST,
        ALBUM
    }

    public enum UserType {
        LISTENER,
        ARTIST,
        HOST
    }

    public enum PageType {
        HOME_PAGE,
        LIKED_CONTENT_PAGE,
        ARTIST_PAGE,
        HOST_PAGE

    }

    public enum SearchType {
        SONG,
        PLAYLIST,
        PODCAST,
        ALBUM,
        ARTIST,
        HOST
    }

    public enum SelectionType {
        FILE,
        PAGE
    }

    public static final int NO_REPEAT = 0;
    public static final int REPEAT_ALL = 1;
    public static final int REPEAT_CURRENT_SONG = 2;
    public static final int REPEAT_ONCE = 1;
    public static final int REPEAT_INFINITE = 2;
    public static final String PUBLIC = "public";
    public static final String PRIVATE = "private";
    public static final int MIN_SECONDS = 90;
    public static final int NO_REPEAT_STATES = 3;

    /**
     * Converts a repeat state and file type to a human-readable string representation.
     *
     * @param repeatState The repeat state value.
     * @param fileType    The file type.
     * @return A string representation of the repeat state and file type.
     */
    public static String repeatStateToString(final int repeatState, final FileType fileType) {
        switch (fileType) {
            case PLAYLIST, ALBUM:
                if (repeatState == REPEAT_ALL) {
                    return "Repeat All";
                } else if (repeatState == REPEAT_CURRENT_SONG) {
                    return "Repeat Current Song";
                }
                break;
            case PODCAST, SONG:
                if (repeatState == REPEAT_ONCE) {
                    return "Repeat Once";
                } else if (repeatState == REPEAT_INFINITE) {
                    return "Repeat Infinite";
                }
                break;
            default:
                break;
        }
        return "No Repeat";
    }

    public static UserType stringToUserType(final String userType) {
        return switch (userType) {
            case "user" -> UserType.LISTENER;
            case "artist" -> UserType.ARTIST;
            case "host" -> UserType.HOST;
            default -> null;
        };
    }

    public static boolean isAudioCollection(FileType fileType) {
        return fileType.equals(FileType.PLAYLIST)
                || fileType.equals(FileType.PODCAST)
                || fileType.equals(FileType.ALBUM);
    }

    public static <T> boolean hasDuplicates(ArrayList<T> list, Function<T, String> getNameFunction) {
        Set<String> itemNames = new HashSet<>();
        for (T item : list) {
            String itemName = getNameFunction.apply(item);
            if (itemNames.contains(itemName)) {
                return true;
            }
            itemNames.add(itemName);
        }
        return false;
    }

    public static PageType searchPage(String nextPage) {
        switch (nextPage) {
            case "Home":
                return PageType.HOME_PAGE;
            case "LikedContent":
                return PageType.LIKED_CONTENT_PAGE;
            case "Artist":
                return PageType.ARTIST_PAGE;
            case "Host":
                return PageType.HOST_PAGE;
            default:
                return null;
        }
    }
}
