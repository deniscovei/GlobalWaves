package data.entities.audioPlayer;

import data.entities.audio.File;
import data.entities.audio.audioFiles.AudioFile;
import lombok.Getter;
import lombok.Setter;
import utils.Extras;

import java.util.ArrayList;

@Setter
@Getter
public final class Player {
    ArrayList<Playable> playerFiles = new ArrayList<>();
    private int currentPlayerFileIndex = -1;
    private boolean shuffleActivated = false;

    /**
     * returns the names of the files in the given list
     */
    public AudioFile getCurrentPlayingFile(final int timestamp) {
        Playable currentPlayerFile = getPlayerFiles().get(getCurrentPlayerFileIndex());
        AudioFile currentPlayingFile = currentPlayerFile.getCurrentPlayingFile(timestamp);
        return currentPlayingFile;
    }

    /**
     * returns true if the loaded file has finished and false otherwise
     */
    public boolean hasFinished(final int timestamp) {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).hasFinished(timestamp);
    }


    /**
     * returns true if the loaded file is paused and false otherwise
     */
    public boolean isPaused() {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).isPaused();
    }

    /**
     * pauses the loaded file
     */
    public void pause(final int timestamp) {
        if (getCurrentPlayerFileIndex() == -1) {
            return;
        }
        getPlayerFiles().get(getCurrentPlayerFileIndex()).pause(timestamp);
    }

    /**
     * plays the loaded file
     */
    public void play(final int timestamp) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).play(timestamp);
    }

    /**
     * returns the remained time of an audio file
     */
    public int getRemainedTime(final AudioFile currentPlayingFile, final int timestamp) {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).
                getRemainedTime(currentPlayingFile, timestamp);
    }

    /**
     * sets the current playing file pause parameter to the given value
     */
    public void setPaused(final boolean value) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).setPaused(value);
    }


    /**
     * loads a new file
     */
    private void loadFile() {
        getPlayerFiles().add(new Playable());
        setCurrentPlayerFileIndex(getPlayerFiles().size() - 1);
    }

    /**
     * adds a new file to the player
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
                    equals(Extras.FileType.SONG)) {
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
                    equals(Extras.FileType.PLAYLIST)) {
                getPlayerFiles().remove(index);
                index--;
            }
        }
    }

    public void removeLoadedAlbums() {
        setCurrentPlayerFileIndex(-1);
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getLoadedFile() == null) {
                continue;
            }

            if (getPlayerFiles().get(index).getLoadedFile().getFileType().
                    equals(Extras.FileType.ALBUM)) {
                getPlayerFiles().remove(index);
                index--;
            }
        }
    }

    /**
     * changes the repeat state of the player
     */
    public void repeat(final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        changeRepeatState();
        //getPlayerFiles().get(getCurrentPlayerFileIndex()).setRepeatState(getRepeatState());
        //setRepeatState(getPlayerFiles().get(getCurrentPlayerFileIndex()).getRepeatState());
    }

    /**
     * get to the current playing file
     */
    private void fastForwardToCurrentPlayingFile(final int timestamp) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).getCurrentPlayingFile(timestamp);

        if (getPlayerFiles().get(getCurrentPlayerFileIndex()).getLoadedFile() == null) {
            setCurrentPlayerFileIndex(-1);
        }
    }

    /**
     * changes the repeat state
     */
    private void changeRepeatState() {
        Playable currentPlayerFIle = (getPlayerFiles().get(getCurrentPlayerFileIndex()));
        currentPlayerFIle.setRepeatState((currentPlayerFIle.getRepeatState() + 1) % Extras.NO_REPEAT_STATES);
    }

    public int getRepeatState() {
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).getRepeatState();
    }

    public void setRepeatState(int repeatState) {
        getPlayerFiles().get(getCurrentPlayerFileIndex()).setRepeatState(repeatState);
    }

    /**
     * sets the loaded file to the selected file
     */
    public void setLoadedFile(final File selectedFile) {
        if (getCurrentPlayerFileIndex() == -1) {
            return;
        }
        getPlayerFiles().get(getCurrentPlayerFileIndex()).setLoadedFile(selectedFile);
    }

    /**
     * returns the loaded file
     */
    public File getLoadedFile() {
        if (getCurrentPlayerFileIndex() == -1) {
            return null;
        }
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).getLoadedFile();
    }

    /**
     * shuffles the songs
     */
    public void shuffle(final int seed, final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        setShuffleActivated(true);
        getPlayerFiles().get(getCurrentPlayerFileIndex()).shuffle(seed);
    }

    /**
     * unshuffles the songs
     */
    public void unshuffle(final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        setShuffleActivated(false);
        getPlayerFiles().get(getCurrentPlayerFileIndex()).unshuffle();
    }

    /**
     * gets to the next song
     */
    public boolean next(final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        if (getCurrentPlayingFile(timestamp) == null) {
            return false;
        }
        return getPlayerFiles().get(getCurrentPlayerFileIndex()).next(timestamp);
    }

    /**
     * gets to the previous song
     */
    public boolean prev(final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        if (getCurrentPlayingFile(timestamp) == null) {
            return false;
        }
        getPlayerFiles().get(getCurrentPlayerFileIndex()).prev(timestamp);
        return true;
    }

    /**
     * forwards the episode
     */
    public void forward(final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        getPlayerFiles().get(getCurrentPlayerFileIndex()).forward(timestamp);
    }

    /**
     * rewinds the episode
     */
    public void backward(final int timestamp) {
        fastForwardToCurrentPlayingFile(timestamp);
        getPlayerFiles().get(getCurrentPlayerFileIndex()).backward(timestamp);
    }
}
