package data.entities.audioPlayer;

import data.entities.audio.File;
import data.entities.audio.audioCollections.AudioCollection;
import data.entities.audio.audioFiles.AudioFile;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Objects;

@Getter
@Setter
public final class Playable {
    private File loadedFile;
    private boolean paused = true;
    private int elapsedTime = 0;
    private int currentPlayingFileId = 0;
    private int startTimestamp = -1;
    private int pauseTimestamp = -1;
    private int offset = 0;
    private int repeatState = AppUtils.NO_REPEAT;
    private boolean shuffleActivated = false;
    private List<Integer> orderedIndexes = new ArrayList<>();
    private List<Integer> shuffledIndexes = new ArrayList<>();
    private List<Integer> indexes = new ArrayList<>();

    /**
     * returns true if the loaded has started and false otherwise
     */
    public boolean hasStarted() {
        return getStartTimestamp() != -1;
    }

    /**
     * returns the current time of the current playing file
     */
    public int getCurrTimeOfFile(final int timestamp) {
        if (!isPaused()) {
            return timestamp - getStartTimestamp() + getOffset() - getElapsedTime();
        } else {
            return getPauseTimestamp() - getStartTimestamp() + getOffset() - getElapsedTime();
        }
    }

    /**
     * returns the remained time of the current playing file
     */
    public int getRemainedTime(final AudioFile file, final int timestamp) {
        return (file == null ? 0 : file.getDuration() - getCurrTimeOfFile(timestamp));
    }

    /**
     * returns true if the loaded file has finished and false otherwise
     */
    public boolean hasFinished(final int timestamp) {
        AudioFile currentPlayingFile = getCurrentPlayingFile(timestamp);

        if (currentPlayingFile == null) {
            setLoadedFile(null);
            return true;
        }

        return getRemainedTime(currentPlayingFile, timestamp) < 0;
    }

    private void prepareIndexes() {
        setIndexesInOrder();
        setIndexes(isShuffleActivated() ? getShuffledIndexes() : getOrderedIndexes());
    }

    /**
     * returns the current playing file
     */
    public AudioFile getCurrentPlayingFile(final int timestamp) {
        if (getLoadedFile().getFileType().equals(AppUtils.FileType.SONG)) {
            AudioFile currAudioFile = (AudioFile) getLoadedFile();
            if (getRemainedTime(currAudioFile, timestamp) >= 0) {
                return currAudioFile;
            } else {
                switch (getRepeatState()) {
                    case AppUtils.REPEAT_ONCE:
                        setRepeatState(AppUtils.NO_REPEAT);
                        setOffset(getOffset() - (currAudioFile).getDuration());
                        return currAudioFile;
                    case AppUtils.REPEAT_INFINITE:
                        while (getRemainedTime(currAudioFile, timestamp) < 0) {
                            setOffset(getOffset() - (currAudioFile).getDuration());
                        }
                        return currAudioFile;
                    case AppUtils.NO_REPEAT:
                        return currAudioFile;
                    default:
                        return null;
                }
            }
        } else if (AppUtils.isAudioCollection(getLoadedFile().getFileType())) {
            AudioCollection audioCollection = (AudioCollection) getLoadedFile();
            AudioFile currentPlayingFile = audioCollection.getAudioFiles().
                get(getCurrentPlayingFileId());

            if (getRemainedTime(currentPlayingFile, timestamp) >= 0) {
                return currentPlayingFile;
            }

            prepareIndexes();

            for (int id = getCurrentPlayingFileId();
                 id < audioCollection.getAudioFiles().size();
                 id = nextId(id)) {
                AudioFile currentFile = audioCollection.getAudioFiles().get(id);

                if (getRemainedTime(currentFile, timestamp) < 0) {
                    setElapsedTime(getElapsedTime() + currentFile.getDuration());
                } else {
                    setCurrentPlayingFileId(id);
                    return currentPlayingFile;
                }
            }

            return null;
        }

        return null;
    }

    /**
     * returns the next id of the current playing file
     * <p>
     * If the shuffle mode is activated, we search for the index of the current playing file in the
     * shuffled indexes list and get what the following index is, otherwise we just get to
     * index + 1. Now, we have the following index of the current playing file, so, keeping track of
     * the repeat state, we return the next id.
     *
     * @param currId      the current playing file id
     */
    private int nextId(final int currId) {
        int id = isShuffleActivated() ? getIndexes().indexOf(currId) : currId;

        int size = ((AudioCollection) getLoadedFile()).getAudioFiles().size();

        switch (getLoadedFile().getFileType()) {
            case PLAYLIST, ALBUM:
                return switch (repeatState) {
                    case AppUtils.REPEAT_ALL -> indexes.get((id + 1) % size);
                    case AppUtils.REPEAT_CURRENT_SONG -> indexes.get(id);
                    default -> // NO_REPEAT
                        indexes.get(id + 1);
                };
            case PODCAST:
                return switch (getRepeatState()) {
                    case AppUtils.REPEAT_ONCE -> {
                        if (indexes.get(id + 1) >= size) {
                            setRepeatState(AppUtils.NO_REPEAT);
                        }
                        yield indexes.get((id + 1) % size);
                    }
                    case AppUtils.REPEAT_INFINITE -> indexes.get((id + 1) % size);
                    default -> // NO_REPEAT
                        indexes.get(id + 1);
                };
            default:
                return indexes.get(id + 1);
        }
    }

    /**
     * plays the loaded file
     */
    public void play(final int timestamp) {
        if (!hasStarted()) {
            setStartTimestamp(timestamp);
        } else {
            setOffset(getOffset() - timestamp + getPauseTimestamp());
        }
        setPaused(false);
    }

    /**
     * pauses the loaded file
     */
    public void pause(final int timestamp) {
        setPauseTimestamp(timestamp);
        setPaused(true);
    }

    /**
     * shuffles the audio files in the loaded file using a seed and sets the shuffled indexes
     */
    public void shuffle(final int seed) {
        setShuffleActivated(true);
        getShuffledIndexes().clear();
        AudioCollection audioCollection = (AudioCollection) getLoadedFile();

        for (int i = 0; i < audioCollection.getAudioFiles().size(); i++) {
            getShuffledIndexes().add(i);
        }

        Collections.shuffle(getShuffledIndexes(), new Random(seed));
        getShuffledIndexes().add(audioCollection.getAudioFiles().size());
    }

    /**
     * sets the audio files indexes in order
     */
    private void setIndexesInOrder() {
        getOrderedIndexes().clear();
        int size = ((AudioCollection) getLoadedFile()).getAudioFiles().size();
        for (int i = 0; i <= size; i++) {
            getOrderedIndexes().add(i);
        }
    }

    /**
     * unshuffles the audio files in the loaded file
     */
    public void unshuffle() {
        setShuffleActivated(false);
    }

    /**
     * skips to the next audio file
     */
    public boolean next(final int timestamp) {
        prepareIndexes();

        if (isPaused()) {
            setOffset(getOffset() + getRemainedTime(getCurrentPlayingFile(timestamp), timestamp));
            play(timestamp);
        } else {
            setOffset(getOffset() + getRemainedTime(getCurrentPlayingFile(timestamp), timestamp));
        }

        setElapsedTime(getElapsedTime()
            + Objects.requireNonNull(getCurrentPlayingFile(timestamp)).getDuration());

        setCurrentPlayingFileId(nextId(getCurrentPlayingFileId()));

        return getCurrentPlayingFileId()
            < ((AudioCollection) getLoadedFile()).getAudioFiles().size();
    }

    /**
     * skips to the previous audio file
     */
    public void prev(final int timestamp) {
        prepareIndexes();
        int id = getCurrentPlayingFileId();
        if (isShuffleActivated()) {
            id = getIndexes().indexOf(id);
        }
        if (id != 0 && getCurrTimeOfFile(timestamp) == 0) {
            setOffset(getOffset()
                - Objects.requireNonNull(getCurrentPlayingFile(timestamp)).getDuration());
            setElapsedTime(getElapsedTime()
                - Objects.requireNonNull(getCurrentPlayingFile(timestamp)).getDuration());
            setCurrentPlayingFileId(getIndexes().get(id - 1));
        }
        setOffset(getOffset() - getCurrTimeOfFile(timestamp));
        if (isPaused()) {
            play(timestamp);
        }
    }

    /**
     * skips 90 seconds
     */
    public void forward(final int timestamp) {
        if (getRemainedTime(getCurrentPlayingFile(timestamp), timestamp) >= AppUtils.MIN_SECONDS) {
            setOffset(getOffset() + AppUtils.MIN_SECONDS);
        } else {
            setOffset(getOffset() + getRemainedTime(getCurrentPlayingFile(timestamp), timestamp));
            setElapsedTime(getElapsedTime()
                + Objects.requireNonNull(getCurrentPlayingFile(timestamp)).getDuration());
            setCurrentPlayingFileId(nextId(getCurrentPlayingFileId()));
        }
    }

    /**
     * rewinds 90 seconds
     */
    public void backward(final int timestamp) {
        if (getCurrTimeOfFile(timestamp) >= AppUtils.MIN_SECONDS) {
            setOffset(getOffset() - AppUtils.MIN_SECONDS);
        } else {
            setOffset(getOffset() - getCurrTimeOfFile(timestamp));
        }
    }
}
