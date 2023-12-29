package fileio.output;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import data.entities.files.audioFiles.AudioFile;
import data.entities.users.Host;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PodcastOutput {
    private final String name;
    private final List<String> episodes = new ArrayList<>();

    public PodcastOutput(final Host host, final int id) {
        this.name = host.getPodcasts().get(id).getName();

        List<AudioFile> podcastEpisodes = host.getPodcasts().get(id).getAudioFiles();
        for (AudioFile episode : podcastEpisodes) {
            this.episodes.add(episode.getName());
        }
    }
}
