package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.files.File;
import data.entities.files.audioFiles.Ad;
import data.entities.files.audioFiles.Song;
import data.entities.users.contentCreator.Artist;
import data.entities.Ranking;
import data.entities.users.Listener;
import data.entities.users.User;
import utils.AppUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.List;
import java.util.LinkedList;

import static utils.AppUtils.CREDIT;
import static utils.AppUtils.ONE_HUNDRED;

public final class EndProgram implements Command {
    @Override
    public Output action(final Input input) {
        Map<String, Ranking> rankings = new HashMap<>();

        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == AppUtils.UserType.LISTENER) {
                Stack<File> songStack = new Stack<>();
                Listener listener = (Listener) user;

                for (File file : listener.getHistory()) {
                    if (file.getFileType() == AppUtils.FileType.AD) {
                        int adPrice = ((Ad) file).getPrice();
                        int noPayedSongs = songStack.size();

                        while (!songStack.isEmpty()) {
                            Song song = (Song) songStack.pop();
                            Artist artist = (Artist) Database.getInstance()
                                .findUser(song.getArtist());
                            if (artist != null) {
                                artist.setSongRevenue(artist.getSongRevenue()
                                    + 1.0 * adPrice / noPayedSongs);
                            }
                        }
                    } else {
                        songStack.push(file);
                    }
                }
            }

            if (user.getUserType() == AppUtils.UserType.ARTIST) {
                Artist artist = (Artist) user;
                Artist.ArtistTops artistTops = (Artist.ArtistTops) user.getTops();

                if (artistTops.getListeners() == 0 && artist.getMerchRevenue() == 0.0) {
                    continue;
                }

                for (Map.Entry<Listener, Integer> listen : artist.getListens().entrySet()) {
                    artist.setSongRevenue(artist.getSongRevenue()
                        + CREDIT * listen.getValue() / listen.getKey().getPremiumListens());

                    for (Map.Entry<Song, Integer> songListen
                        : listen.getKey().getListensPerSong().entrySet()) {
                        if (!songListen.getKey().getArtist().equals(artist.getUsername())) {
                            continue;
                        }

                        if (!artist.getRevenuePerSong().containsKey(songListen.getKey())) {
                            artist.getRevenuePerSong().put(songListen.getKey(),
                                CREDIT * songListen.getValue()
                                    / listen.getKey().getPremiumListens());
                        } else {
                            artist.getRevenuePerSong().put(songListen.getKey(),
                                artist.getRevenuePerSong().get(songListen.getKey())
                                    + CREDIT * songListen.getValue()
                                    / listen.getKey().getPremiumListens());
                        }
                    }

                }

                Song mostProfitableSong = null;
                for (Map.Entry<Song, Double> songRevenue
                    : artist.getRevenuePerSong().entrySet()) {
                    if (mostProfitableSong == null
                        || Double.compare(songRevenue.getValue(),
                        artist.getRevenuePerSong().get(mostProfitableSong)) > 0
                        || (Double.compare(songRevenue.getValue(),
                        artist.getRevenuePerSong().get(mostProfitableSong)) == 0
                        && songRevenue.getKey().getName()
                        .compareTo(mostProfitableSong.getName()) < 0)) {
                        mostProfitableSong = songRevenue.getKey();
                    }
                }

                artist.setSongRevenue(Math.round(artist.getSongRevenue() * ONE_HUNDRED)
                    / ONE_HUNDRED);

                rankings.put(artist.getUsername(),
                    new Ranking(artist.getMerchRevenue(), artist.getSongRevenue(),
                        mostProfitableSong == null ? "N/A" : mostProfitableSong.getName()));
            }
        }

        rankings = sortMap(rankings);

        int rank = 1;
        for (Map.Entry<String, Ranking> entry : rankings.entrySet()) {
            Artist artist = (Artist) Database.getInstance().findUser(entry.getKey());
            Artist.ArtistTops artistTops = (Artist.ArtistTops) artist.getTops();

            entry.getValue().setRanking(rank++);
        }

        return new Output.Builder()
            .command(input.getCommand())
            .result(rankings)
            .build();
    }

    Map<String, Ranking> sortMap(final Map<String, Ranking> unsortedMap) {
        List<Map.Entry<String, Ranking>> list = new LinkedList<>(unsortedMap.entrySet());

        list.sort((o1, o2) -> {
            if (o1.getValue().getMerchRevenue() + o1.getValue().getSongRevenue()
                == o2.getValue().getMerchRevenue() + o2.getValue().getSongRevenue()) {
                return o1.getKey().compareTo(o2.getKey());
            }

            return Double.compare(o2.getValue().getMerchRevenue() + o2.getValue().getSongRevenue(),
                o1.getValue().getMerchRevenue() + o1.getValue().getSongRevenue());
        });

        LinkedHashMap<String, Ranking> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Ranking> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
