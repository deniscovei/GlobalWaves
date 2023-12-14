package utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class AppUtils {
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
    public static final int MIN_YEAR = 1900;
    public static final int CURR_YEAR = 2023;
    public static final int NO_MONTHS = 12;
    public static final int MAX_NO_DAYS_IN_A_MONTH = 31;
    public static final int MIN_NO_DAYS_IN_FEBRUARY = 28;
    public static final int FEBRUARY = 2;
    public static final int DAY_START_POS = 0;
    public static final int DAY_END_POS = 2;
    public static final int MONTH_START_POS = 3;
    public static final int MONTH_END_POS = 5;
    public static final int YEAR_START_POS = 6;
    public static final int YEAR_END_POS = 10;
    public static final int DATE_LENGTH = 10;

    private AppUtils() {
        // private constructor to prevent instantiation
        throw new AssertionError("Utility class should not be instantiated");
    }

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

    /**
     * converts a string to a repeat state
     */
    public static UserType stringToUserType(final String userType) {
        return switch (userType) {
            case "user" -> UserType.LISTENER;
            case "artist" -> UserType.ARTIST;
            case "host" -> UserType.HOST;
            default -> null;
        };
    }

    /**
     * checks if a file type is an audio collection
     */
    public static boolean isAudioCollection(final FileType fileType) {
        return fileType.equals(FileType.PLAYLIST)
                || fileType.equals(FileType.PODCAST)
                || fileType.equals(FileType.ALBUM);
    }

    /**
     * Checks if a list of items has duplicates.
     */
    public static <T> boolean hasDuplicates(final List<T> list,
                                            final Function<T, String> getNameFunction) {
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

    /**
     * Converts a page type to a human-readable string representation.
     *
     * @param pageType The page type.
     * @return A string representation of the page type.
     */
    public static PageType searchPage(final String pageType) {
        return switch (pageType) {
            case "Home" -> PageType.HOME_PAGE;
            case "LikedContent" -> PageType.LIKED_CONTENT_PAGE;
            case "Artist" -> PageType.ARTIST_PAGE;
            case "Host" -> PageType.HOST_PAGE;
            default -> null;
        };
    }
}
