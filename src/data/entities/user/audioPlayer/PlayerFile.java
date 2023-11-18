package data.entities.user.audioPlayer;

import data.entities.audio.File;
import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioFiles.AudioFile;
import lombok.Getter;
import lombok.Setter;
import utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
    private boolean shuffleActivated = false;
    private ArrayList<Integer> orderedIndexes = new ArrayList<>();
    private ArrayList<Integer> shuffledIndexes = new ArrayList<>();
    private ArrayList<Integer> indexes = new ArrayList<>();

    public PlayerFile(File file) {
        this.ongoingAudioFile = file;
    }

    public boolean hasStarted() {
        return startTimestamp != -1;
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
            setLoadedFile(null);
            System.out.println("NU VERM SA INTRAM AICI");
            return true;
        }

        if (getRemainedTime(currentPlayingFile, timestamp) <= 0) {
            System.out.println("NU VERM SA INTRAM NICI AICI");
            return true;
        }

        return false;
    }

    public void prepareIndexes() {
        setIndexesInOrder();
        if (isShuffleActivated()) {
            indexes = shuffledIndexes;
            for (int id = 0; id < indexes.size(); id++) {
                if (indexes.get(id) == currentPlayingFileId) {
                    currentPlayingFileId = id;
                    break;
                }
            }
        } else {
            indexes = orderedIndexes;
        }
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

            prepareIndexes();

            for (int id = currentPlayingFileId; id < audioCollection.getAudioFiles().size(); id = nextId(id, indexes)) {
                currentPlayingFile = audioCollection.getAudioFiles().get(indexes.get(id));
                if (getRemainedTime(currentPlayingFile, timestamp) < 0) {
                    timePassed += currentPlayingFile.getDuration();
                } else {
                    currentPlayingFileId = id;
                    return currentPlayingFile;
                }
            }

            return null;
        }

        return null;
    }

    private int nextId(int id, ArrayList<Integer> indexes) {
        int size = ((AudioCollection) loadedFile).getAudioFiles().size();
        switch (loadedFile.getFileType()) {
            case PLAYLIST:
                switch (repeatState) {
                    case Constants.NO_REPEAT:
                        return indexes.get(id + 1);
                    case Constants.REPEAT_ALL:
                        return indexes.get((id + 1) % size);
                    case Constants.REPEAT_CURRENT_SONG:
                        return id;
                }
            case PODCAST:
                switch (repeatState) {
                    case Constants.NO_REPEAT:
                        return indexes.get(id + 1);
                    case Constants.REPEAT_ONCE:
                        if (indexes.get(id + 1) >= size) {
                            setRepeatState(Constants.NO_REPEAT);
                            return indexes.get(0);
                        } else {
                            return indexes.get(id + 1);
                        }
                    case Constants.REPEAT_INFINITE:
                        return indexes.get((id + 1) % size);
                }
        }
        return indexes.get(id + 1);
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

    public void shuffle(int seed) {
        setShuffleActivated(true);
        shuffledIndexes.clear();
        for (int i = 0; i < ((AudioCollection) loadedFile).getAudioFiles().size(); i++) {
            shuffledIndexes.add(i);
        }
        Collections.shuffle(shuffledIndexes, new Random(seed));
        shuffledIndexes.add(((AudioCollection) loadedFile).getAudioFiles().size());
    }

    public void setIndexesInOrder() {
        orderedIndexes.clear();
        int size = ((AudioCollection) loadedFile).getAudioFiles().size();
        System.out.println("SIZE: " + size);
        for (int i = 0; i <= size; i++) {
            orderedIndexes.add(i);
        }
    }

    public void unshuffle() {
        setShuffleActivated(false);
    }

    public void next(int timestamp) {
        prepareIndexes();
        setOffset(getOffset() + getRemainedTime(getCurrentPlayingFile(timestamp), timestamp));
        timePassed += getCurrentPlayingFile(timestamp).getDuration();
        currentPlayingFileId = nextId(currentPlayingFileId, indexes);
    }

    public void prev(int timestamp) {
        prepareIndexes();
        if (currentPlayingFileId != 0 && getCurrTimeOfFile(timestamp) == 0) {
            setOffset(getOffset() - getCurrentPlayingFile(timestamp).getDuration());
            timePassed -= getCurrentPlayingFile(timestamp).getDuration();
            currentPlayingFileId = indexes.get(currentPlayingFileId - 1);
        }
        setOffset(getOffset() - getCurrTimeOfFile(timestamp));
    }

    public void forward(int timestamp) {
        if (getRemainedTime(getCurrentPlayingFile(timestamp), timestamp) >= 90) {
            setOffset(getOffset() + 90);
        } else {
            setOffset(getOffset() + getRemainedTime(getCurrentPlayingFile(timestamp), timestamp));
            timePassed += getCurrentPlayingFile(timestamp).getDuration();
            currentPlayingFileId = nextId(currentPlayingFileId, indexes);
        }
    }

    public void backward(int timestamp) {
        if (getCurrTimeOfFile(timestamp) >= 90) {
            setOffset(getOffset() - 90);
        } else {
            setOffset(getOffset() - getCurrTimeOfFile(timestamp));
        }
    }
}
