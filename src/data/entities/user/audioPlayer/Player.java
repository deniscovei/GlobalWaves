package data.entities.user.audioPlayer;

import data.entities.audio.File;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.audio.audioFiles.Song;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;

@Setter
@Getter
public class Player {
    ArrayList <PlayerFile> playerFiles = new ArrayList <>();
    private int currentPlayerFileIndex = -1;
    private int repeatState = Constants.NO_REPEAT;

    public File getSelection() {
        if (currentPlayerFileIndex == -1) {
            return null;
        }
        return playerFiles.get(currentPlayerFileIndex).getOngoingAudioFile();
    }

    public void setSelection(File selection) {
        if (currentPlayerFileIndex == -1) {
            return;
        }
        playerFiles.get(currentPlayerFileIndex).setOngoingAudioFile(selection);
    }

    public AudioFile getCurrentPlayingFile(int timestamp) {
        AudioFile currentPlayingFile = playerFiles.get(currentPlayerFileIndex).getCurrentPlayingFile(timestamp);
        setRepeatState(playerFiles.get(currentPlayerFileIndex).getRepeatState());
        return currentPlayingFile;
    }

    public boolean hasFinished(int timestamp) {
        return playerFiles.get(currentPlayerFileIndex).hasFinished(timestamp);
    }


    public boolean isPaused() {
        return playerFiles.get(currentPlayerFileIndex).isPaused();
    }

    public void pause(int timestamp) {
        if (currentPlayerFileIndex == -1) {
            return;
        }
        playerFiles.get(currentPlayerFileIndex).pause(timestamp);
    }

    public void play(int timestamp) {
        playerFiles.get(currentPlayerFileIndex).play(timestamp);
    }

    public int getRemainedTime(AudioFile currentPlayingFile, int timestamp) {
        return playerFiles.get(currentPlayerFileIndex).getRemainedTime(currentPlayingFile, timestamp);
    }

    public void setPaused(boolean value) {
        playerFiles.get(currentPlayerFileIndex).setPaused(value);
    }

    private void loadFile(File file) {
        playerFiles.add(new PlayerFile(file));
        setCurrentPlayerFileIndex(playerFiles.size() - 1);
    }

    public void select(File selection) {
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getOngoingAudioFile().getName().equals(selection.getName())) {
                setCurrentPlayerFileIndex(index);
                return;
            }
        }
        loadFile(selection);
    }

    public void removeLoadedSongs() {
        setCurrentPlayerFileIndex(-1);
        for (int index = 0; index < getPlayerFiles().size(); index++) {
            if (getPlayerFiles().get(index).getOngoingAudioFile() instanceof Song) {
                getPlayerFiles().remove(index);
                index--;
            }
        }
    }

    public void repeat() {
        changeRepeatState();
        playerFiles.get(currentPlayerFileIndex).setRepeatState(getRepeatState());
        setRepeatState(playerFiles.get(currentPlayerFileIndex).getRepeatState());
    }

    private void changeRepeatState() {
        setRepeatState((getRepeatState() + 1) % 3);
    }

    public void setLoadedFile(File selectedFile) {
        if (currentPlayerFileIndex == -1) {
            return;
        }
        playerFiles.get(currentPlayerFileIndex).setLoadedFile(selectedFile);
    }

    public File getLoadedFile() {
        if (currentPlayerFileIndex == -1) {
            return null;
        }
        return playerFiles.get(currentPlayerFileIndex).getLoadedFile();
    }
}
