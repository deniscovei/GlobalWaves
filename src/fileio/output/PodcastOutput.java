package fileio.output;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import data.entities.audio.audioFiles.AudioFile;
import data.entities.users.Artist;
import data.entities.users.Host;

import java.util.ArrayList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PodcastOutput {
    String name = null;
    ArrayList<String> episodes = new ArrayList<>();

    public PodcastOutput() {
    }

    public PodcastOutput(Host host, int id) {
        this.name = host.getPodcasts().get(id).getName();

        ArrayList<AudioFile> episodes = host.getPodcasts().get(id).getAudioFiles();
        for (AudioFile episode : episodes) {
            this.episodes.add(episode.getName());
        }
    }
}
