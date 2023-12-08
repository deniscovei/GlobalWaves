package utils;

public final class Constants {
    public static final int RES_COUNT_MAX = 5;

    public enum FileType {
        SONG,
        EPISODE,
        PODCAST,
        PLAYLIST
    }

    public enum UserType {
        LISTENER,
        ARTIST,
        HOST
    }

    public enum Page {
        HOME_PAGE,
        LIKED_CONTENT_PAGE,
        ARTIST_PAGE,
        HOST_PAGE

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
     * @param fileType The file type.
     * @return A string representation of the repeat state and file type.
     */
    public static String repeatStateToString(final int repeatState, final FileType fileType) {
        switch (fileType) {
            case PLAYLIST:
                if (repeatState == REPEAT_ALL) {
                    return "Repeat All";
                } else if (repeatState == REPEAT_CURRENT_SONG) {
                    return "Repeat Current Song";
                }
                break;
            case PODCAST:
            case SONG:
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
}
