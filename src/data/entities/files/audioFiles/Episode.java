package data.entities.files.audioFiles;

import data.Database;
import data.entities.users.contentCreator.Host;
import data.entities.users.Listener;
import data.entities.users.User;
import fileio.input.EpisodeInput;
import lombok.Getter;
import lombok.Setter;
import utils.AppUtils;
import utils.AppUtils.FileType;

import java.util.Objects;

/**
 * The type Episode.
 */
@Setter
@Getter
public final class Episode extends AudioFile {
    private String description;
    private String host;

    /**
     * Instantiates a new Episode.
     *
     * @param episode the episode
     */
    public Episode(final EpisodeInput episode, final String host) {
        super(episode.getName(), episode.getDuration());
        this.fileType = FileType.EPISODE;
        this.description = episode.getDescription();
        this.host = host;
    }

    public void listen(final Listener listener) {
        //System.out.println("Listener " + listener.getUsername() + " listened to episode " +
        // getName());
        Listener.ListenerTops tops = (Listener.ListenerTops) listener.getTops();

        tops.getTopEpisodes().compute(getName(), (episode, count) -> (count == null) ? 1 : count + 1);

        Host host = null;

        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == AppUtils.UserType.HOST) {
                Host currHost = (Host) user;

                if (currHost.getUsername().equals(getHost())) {
                    host = currHost;
                }
            }
        }

        if (host == null) {
            host = new Host(getHost());
            Database.getInstance().addUser(host);
        }

        Host.HostTops hostTops = (Host.HostTops) Objects.requireNonNull(host).getTops();

        hostTops.getTopEpisodes().compute(getName(), (episode, count) -> (count == null) ? 1 : count + 1);

        hostTops.getTopFans().add(listener.getUsername());

//        if (listener.isPremium()) {
//            listener.setPremiumListens(listener.getPremiumListens() + 1);
//
//            if (!listener.getListensPerSong().containsKey(this)) {
//                listener.getListensPerSong().put(this, 1);
//            } else {
//                listener.getListensPerSong().put(this,
//                    listener.getListensPerSong().get(this) + 1);
//            }
//
//            if (!host.getListens().containsKey(listener)) {
//                host.getListens().put(listener, 1);
//            } else {
//                host.getListens().put(listener, host.getListens().get(listener) + 1);
//            }
//        }

//        listener.setTotalListens(listener.getTotalListens() + 1);
//        listener.getHistory().add(this);
    }
}
