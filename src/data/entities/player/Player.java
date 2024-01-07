package data.entities.player;

import data.entities.files.File;
import data.entities.files.audioFiles.AudioFile;
import data.entities.users.Listener;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Player.
 */
@Setter
@Getter
public final class Player {
    private List<Playable> playerFiles = new ArrayList<>();
    private Listener listener = null;
    private int currentPlayerFileIndex = -1;
    private boolean shuffleActivated = false;

    /**
     * returns the names of the files in the given list
     *
     * @param timestamp the timestamp
     * @return the current playing file
     */
    public AudioFile getCurrentPlayingFile(final int timestamp) {
        Playable currentPlayerFile = getPlayerFiles().get(getCurrentPlayerFileIndex());
        return currentPlayerFile.getCurrentPlayingFile(timestamp);
    }

    /**
     * returns true if the loaded file has finished and false otherwise
     *
     * @param timestamp the timestamp
     * @return the boolean
     */
    public boolean hasFinished(final int timestamp) {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).hasFinished(timestamp);
    }

    /**
     * get to the current playing file
     */
    public void simulateTime(final int timestamp) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).simulateTime(timestamp);
    }


    /**
     * returns true if the loaded file is paused and false otherwise
     *
     * @return the boolean
     */
    public boolean isPaused() {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).isPaused();
    }

    /**
     * pauses the loaded file
     *
     * @param timestamp the timestamp
     */
    public void pause(final int timestamp) {
        if (getCurrentPlayerFileIndex() == -1) {
            return;
        }
        getPlayerFiles().get(getCurrentPlayerFileIndex()).pause(timestamp);
    }

    /**
     * plays the loaded file
     *
     * @param timestamp the timestamp
     */
    public void play(final int timestamp) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).play(timestamp);
    }

    public int getCurrTimeOfFile(final int timestamp) {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).getCurrTimeOfFile(timestamp);
    }

    /**
     * returns the remained time of an audio file
     *
     * @param currentPlayingFile the current playing file
     * @param timestamp          the timestamp
     * @return the remained time
     */
    public int getRemainedTime(final AudioFile currentPlayingFile, final int timestamp) {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).
                getRemainedTime(currentPlayingFile, timestamp);
    }

    /**
     * sets the current playing file pause parameter to the given value
     *
     * @param value the value
     */
    public void setPaused(final boolean value) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).setPaused(value);
    }


    /**
     * loads a new file
     */
    private void loadFile() {
        getPlayerFiles().add(new Playable(getListener()));
        setCurrentPlayerFileIndex(getPlayerFiles().size() - 1);
    }

    /**
     * adds a new file to the player
     *
     * @param selection the selection
     */
    public void select(final File selection) {
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getLoadedFile() == null) {
                continue;
            }
            if (getPlayerFiles().get(index).getLoadedFile().getName().equals(selection.getName())) {
                setCurrentPlayerFileIndex(index);
                return;
            }
        }
        loadFile();
    }

    /**
     * removes loaded songs from the player
     */
    public void removeLoadedSongs() {
        setCurrentPlayerFileIndex(-1);
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getLoadedFile() == null) {
                continue;
            }
            if (getPlayerFiles().get(index).getLoadedFile().getFileType().
                    equals(AppUtils.FileType.SONG)) {
                getPlayerFiles().remove(index);
                index--;
            }
        }
    }

    /**
     * removes loaded playlists from the player
     */
    public void removeLoadedPlaylists() {
        setCurrentPlayerFileIndex(-1);
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getLoadedFile() == null) {
                continue;
            }

            if (getPlayerFiles().get(index).getLoadedFile().getFileType().
                    equals(AppUtils.FileType.PLAYLIST)) {
                getPlayerFiles().remove(index);
                index--;
            }
        }
    }

    /**
     * removes loaded albums from the player
     */
    public void removeLoadedAlbums() {
        setCurrentPlayerFileIndex(-1);
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getLoadedFile() == null) {
                continue;
            }

            if (getPlayerFiles().get(index).getLoadedFile().getFileType().
                    equals(AppUtils.FileType.ALBUM)) {
                getPlayerFiles().remove(index);
                index--;
            }
        }
    }

    /**
     * changes the repeat state of the player
     *
     * @param timestamp the timestamp
     */
    public void repeat(final int timestamp) {
        //simulateTime(timestamp);
        changeRepeatState();
    }

    /**
     * changes the repeat state
     */
    private void changeRepeatState() {
        Playable currentPlayerFIle = (getPlayerFiles().get(getCurrentPlayerFileIndex()));
        currentPlayerFIle.setRepeatState((currentPlayerFIle.getRepeatState() + 1)
            % AppUtils.NO_REPEAT_STATES);
    }

    /**
     * returns the repeat state
     *
     * @return the repeat state
     */
    public int getRepeatState() {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).getRepeatState();
    }

    /**
     * sets the loaded file to the selected file
     *
     * @param selectedFile the selected file
     */
    public void setLoadedFile(final File selectedFile) {
        if (getCurrentPlayerFileIndex() == -1) {
            return;
        }
        getPlayerFiles().get(getCurrentPlayerFileIndex()).setLoadedFile(selectedFile);
    }

    /**
     * returns the loaded file
     *
     * @return the loaded file
     */
    public File getLoadedFile() {
        if (getCurrentPlayerFileIndex() == -1) {
            return null;
        }
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).getLoadedFile();
    }

    /**
     * shuffles the songs
     *
     * @param seed      the seed
     * @param timestamp the timestamp
     */
    public void shuffle(final int seed, final int timestamp) {
        setShuffleActivated(true);
        getPlayerFiles().get(getCurrentPlayerFileIndex()).shuffle(seed);
    }

    /**
     * unshuffles the songs
     *
     * @param timestamp the timestamp
     */
    public void unshuffle(final int timestamp) {
        setShuffleActivated(false);
        getPlayerFiles().get(getCurrentPlayerFileIndex()).unshuffle();
    }

    /**
     * gets to the next song
     *
     * @param timestamp the timestamp
     * @return the boolean
     */
    public boolean next(final int timestamp) {
        if (getCurrentPlayingFile(timestamp) == null) {
            return false;
        }

        return getPlayerFiles().get(getCurrentPlayerFileIndex()).next(timestamp);
    }

    /**
     * gets to the previous song
     *
     * @param timestamp the timestamp
     * @return the boolean
     */
    public boolean prev(final int timestamp) {
        if (getCurrentPlayingFile(timestamp) == null) {
            return false;
        }
        getPlayerFiles().get(getCurrentPlayerFileIndex()).prev(timestamp);
        return true;
    }

    /**
     * forwards the episode
     *
     * @param timestamp the timestamp
     */
    public void forward(final int timestamp) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).forward(timestamp);
    }

    /**
     * rewinds the episode
     *
     * @param timestamp the timestamp
     */
    public void backward(final int timestamp) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).backward(timestamp);
    }
}
