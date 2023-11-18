package data.entities.user.audioPlayer;

import data.entities.audio.File;
import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioCollections.Playlist;
import data.entities.audio.audioFiles.AudioFile;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

@Getter
@Setter
public class PlayerFile {
    private File loadedFile;
    private File ongoingAudioFile;
    private boolean paused = true;
    private int timePassed = 0;
    private int currentPlayingFileId = 0;
    private int startTimestamp = -1;
    private int pauseTimestamp = -1;
    private int offset = 0;
    private int repeatState = Constants.NO_REPEAT;

    public PlayerFile(File file) {
        this.ongoingAudioFile = file;
    }

    public boolean hasStarted() {
        return startTimestamp != -1;
    }

    public void play(int timestamp) {
        if (!hasStarted()) {
            setStartTimestamp(timestamp);
        } else {
            setOffset(getOffset() - timestamp + getPauseTimestamp());
        }
        setPaused(false);
    }

    public void pause(int timestamp) {
        setPauseTimestamp(timestamp);
        setPaused(true);
    }

    public int getCurrTimeOfFile(int timestamp) {
        if (!isPaused()) {
            return timestamp - startTimestamp + offset - timePassed;
        } else {
            return pauseTimestamp - startTimestamp + offset - timePassed;
        }
    }

    public int getRemainedTime(AudioFile file, int timestamp) {
        if (file == null) {
            return 0;
        }
        return file.getDuration() - getCurrTimeOfFile(timestamp);
    }

    public boolean hasFinished(int timestamp) {
        System.out.println("VERIFICARE FINISHED: " + timestamp);
        System.out.println("VERIFICARE REPEAT STATE: " + getRepeatState());

        AudioFile currentPlayingFile = getCurrentPlayingFile(timestamp);

        if (currentPlayingFile == null) {
            System.out.println("NU VERM SA INTRAM AICI");
            return true;
        }

        if (getRemainedTime(currentPlayingFile, timestamp) <= 0) {
            System.out.println("NU VERM SA INTRAM NICI AICI");
            return true;
        }

        return false;
    }

    public AudioFile getCurrentPlayingFile(int timestamp) {
        if (loadedFile.getFileType().equals(Constants.FileType.SONG)) {
            if (getRemainedTime((AudioFile) ongoingAudioFile, timestamp) >= 0) {
                return (AudioFile) ongoingAudioFile;
            } else {
                switch (getRepeatState()) {
                    case Constants.NO_REPEAT:
                        return null;
                    case Constants.REPEAT_ONCE:
                        setRepeatState(Constants.NO_REPEAT);
                        setOffset(getOffset() - ((AudioFile) ongoingAudioFile).getDuration());
                        return (AudioFile) ongoingAudioFile;
                    case Constants.REPEAT_INFINITE:
                        while (getRemainedTime((AudioFile) ongoingAudioFile, timestamp) < 0) {
                            setOffset(getOffset() - ((AudioFile) ongoingAudioFile).getDuration());
                        }
                        return (AudioFile) ongoingAudioFile;
                }
            }
        } else if (loadedFile.getFileType().equals(Constants.FileType.PLAYLIST) ||
                   loadedFile.getFileType().equals(Constants.FileType.PODCAST)) {
            AudioCollection audioCollection = (AudioCollection) loadedFile;
            AudioFile currentPlayingFile = audioCollection.getAudioFiles().get(currentPlayingFileId);
            if (getRemainedTime(currentPlayingFile, timestamp) >= 0)
                return currentPlayingFile;

            for (int id = currentPlayingFileId; id < audioCollection.getAudioFiles().size(); id = nextId(id)) {
                currentPlayingFile = audioCollection.getAudioFiles().get(id);
                if (getRemainedTime(currentPlayingFile, timestamp) < 0) {
                    timePassed += currentPlayingFile.getDuration();
                } else {
                    currentPlayingFileId = id;
                    return currentPlayingFile;
                }
            }

            return audioCollection.getAudioFiles().get(audioCollection.getAudioFiles().size() - 1);
        }

        return null;
    }

    private int nextId(int id) {
        switch (loadedFile.getFileType()) {
            case PLAYLIST:
                switch (repeatState) {
                    case Constants.NO_REPEAT:
                        return id + 1;
                    case Constants.REPEAT_ALL:
                        return (id + 1) % ((AudioCollection) loadedFile).getAudioFiles().size();
                    case Constants.REPEAT_CURRENT_SONG:
                        return id;
                }
            case PODCAST:
                switch (repeatState) {
                    case Constants.NO_REPEAT:
                        return id + 1;
                    case Constants.REPEAT_ONCE:
                        if (id + 1 >= ((AudioCollection) loadedFile).getAudioFiles().size()) {
                            setRepeatState(Constants.NO_REPEAT);
                            return 0;
                        } else {
                            return id + 1;
                        }
                    case Constants.REPEAT_INFINITE:
                        return (id + 1) % ((AudioCollection) loadedFile).getAudioFiles().size();
                }
        }
        return id + 1;
    }
}
