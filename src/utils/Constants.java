package utils;

public final class Constants {
    public static final String SELECT_COMMAND = "select";
    public static final String SEARCH = "search";

    public enum FileType {
        SONG,
        EPISODE,
        PODCAST,
        PLAYLIST
    }

    public static final int NO_REPEAT = 0;
    public static final int REPEAT_ALL = 1;
    public static final int REPEAT_CURRENT_SONG = 2;
    public static final int REPEAT_ONCE = 1;
    public static final int REPEAT_INFINITE = 2;

    public static String repeatStateToString(int repeatState, FileType ongoingAudioFileType) {
        switch (ongoingAudioFileType) {
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
        }
        return "No Repeat";
    }
}
