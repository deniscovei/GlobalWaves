package data.entities.pages;

import data.entities.files.audioCollections.Podcast;
import data.entities.files.audioFiles.AudioFile;
import data.entities.files.audioFiles.Episode;
import data.entities.content.Announcement;
import data.entities.users.contentCreator.Host;
import data.entities.users.User;
import utils.AppUtils.PageType;

/**
 * The type Host page.
 */
public final class HostPage extends Page {
    /**
     * Instantiates a new Host page.
     *
     * @param creator the creator
     */
    public HostPage(final User creator) {
        super(creator);
        pageType = PageType.HOST_PAGE;
    }
    @Override
    public String getFormat() {
        Host host = (Host) getCreator();
        StringBuilder result = new StringBuilder("Podcasts:\n\t[");

        for (Podcast podcast : host.getPodcasts()) {
            result.append(podcast.getName()).append(":\n\t[");
            for (AudioFile episode : podcast.getEpisodes()) {
                result.append(episode.getName()).append(" - ")
                    .append(((Episode) episode).getDescription());
                if (episode != podcast.getEpisodes().get(podcast.getEpisodes().size() - 1)) {
                    result.append(", ");
                }
            }

            result.append("]\n");

            if (podcast != host.getPodcasts().get(host.getPodcasts().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]\n\nAnnouncements:\n\t[");

        for (Announcement announcement : host.getAnnouncements()) {
            result.append(announcement.getName()).append(":\n\t")
                .append(announcement.getDescription()).append("\n");

            if (announcement != host.getAnnouncements().get(host.getAnnouncements().size() - 1)) {
                result.append(", ");
            }
        }

        result.append("]");

        return result.toString();
    }
}
