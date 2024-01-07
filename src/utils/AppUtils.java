package utils;

import java.util.*;
import java.util.function.Function;

/**
 * The type App utils.
 */
public final class AppUtils {
    /**
     * The constant RES_COUNT_MAX.
     */
    public static final int RES_COUNT_MAX = 5;

    /**
     * The enum File type.
     */
    public enum FileType {
        SONG,
        EPISODE,
        PODCAST,
        PLAYLIST,
        ALBUM,
        AD
    }

    /**
     * The enum User type.
     */
    public enum UserType {
        /**
         * Listener user type.
         */
        LISTENER,
        /**
         * Artist user type.
         */
        ARTIST,
        /**
         * Host user type.
         */
        HOST
    }

    public enum RecommendationType {
        RANDOM_SONG,
        RANDOM_PLAYLIST,
        FANS_PLAYLIST
    }

    /**
     * The enum Page type.
     */
    public enum PageType {
        /**
         * Home page type.
         */
        HOME_PAGE,
        /**
         * Liked content page type.
         */
        LIKED_CONTENT_PAGE,
        /**
         * Artist page type.
         */
        ARTIST_PAGE,
        /**
         * Host page type.
         */
        HOST_PAGE

    }

    /**
     * The enum Search type.
     */
    public enum SearchType {
        /**
         * Song search type.
         */
        SONG,
        /**
         * Playlist search type.
         */
        PLAYLIST,
        /**
         * Podcast search type.
         */
        PODCAST,
        /**
         * Album search type.
         */
        ALBUM,
        /**
         * Artist search type.
         */
        ARTIST,
        /**
         * Host search type.
         */
        HOST
    }

    /**
     * The enum Selection type.
     */
    public enum SelectionType {
        /**
         * File selection type.
         */
        FILE,
        /**
         * Page selection type.
         */
        PAGE
    }

    /**
     * The constant NO_REPEAT.
     */
    public static final int NO_REPEAT = 0;
    /**
     * The constant REPEAT_ALL.
     */
    public static final int REPEAT_ALL = 1;
    /**
     * The constant REPEAT_CURRENT_SONG.
     */
    public static final int REPEAT_CURRENT_SONG = 2;
    /**
     * The constant REPEAT_ONCE.
     */
    public static final int REPEAT_ONCE = 1;
    /**
     * The constant REPEAT_INFINITE.
     */
    public static final int REPEAT_INFINITE = 2;
    /**
     * The constant PUBLIC.
     */
    public static final String PUBLIC = "public";
    /**
     * The constant PRIVATE.
     */
    public static final String PRIVATE = "private";
    /**
     * The constant MIN_SECONDS.
     */
    public static final int MIN_SECONDS = 90;
    /**
     * The constant NO_REPEAT_STATES.
     */
    public static final int NO_REPEAT_STATES = 3;
    /**
     * The constant MIN_YEAR.
     */
    public static final int MIN_YEAR = 1900;
    /**
     * The constant CURR_YEAR.
     */
    public static final int CURR_YEAR = 2023;
    /**
     * The constant NO_MONTHS.
     */
    public static final int NO_MONTHS = 12;
    /**
     * The constant MAX_NO_DAYS_IN_A_MONTH.
     */
    public static final int MAX_NO_DAYS_IN_A_MONTH = 31;
    /**
     * The constant MIN_NO_DAYS_IN_FEBRUARY.
     */
    public static final int MIN_NO_DAYS_IN_FEBRUARY = 28;
    /**
     * The constant FEBRUARY.
     */
    public static final int FEBRUARY = 2;
    /**
     * The constant DAY_START_POS.
     */
    public static final int DAY_START_POS = 0;
    /**
     * The constant DAY_END_POS.
     */
    public static final int DAY_END_POS = 2;
    /**
     * The constant MONTH_START_POS.
     */
    public static final int MONTH_START_POS = 3;
    /**
     * The constant MONTH_END_POS.
     */
    public static final int MONTH_END_POS = 5;
    /**
     * The constant YEAR_START_POS.
     */
    public static final int YEAR_START_POS = 6;
    /**
     * The constant YEAR_END_POS.
     */
    public static final int YEAR_END_POS = 10;
    /**
     * The constant DATE_LENGTH.
     */
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
     *
     * @param userType the user type
     * @return the user type
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
     *
     * @param fileType the file type
     * @return the boolean
     */
    public static boolean isAudioCollection(final FileType fileType) {
        return fileType.equals(FileType.PLAYLIST)
                || fileType.equals(FileType.PODCAST)
                || fileType.equals(FileType.ALBUM);
    }

    /**
     * Checks if a list of items has duplicates.
     *
     * @param <T>             the type parameter
     * @param list            the list
     * @param getNameFunction the get name function
     * @return the boolean
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

    public static RecommendationType stringToRecommendationType(final String recommendationType) {
        return switch (recommendationType) {
            case "random_song" -> RecommendationType.RANDOM_SONG;
            case "random_playlist" -> RecommendationType.RANDOM_PLAYLIST;
            case "fans_playlist" -> RecommendationType.FANS_PLAYLIST;
            default -> null;
        };
    }

    public static Map<String, Integer> sortMap(Map<String, Integer> unsortedMap, int resCountMax) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        list.sort(Comparator
            .comparing(Map.Entry<String, Integer>::getValue).reversed()
            .thenComparing(Map.Entry::getKey));

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        int count = 0;

        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());

            if (++count == resCountMax) {
                break;
            }
        }

        return sortedMap;
    }
}
