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

import java.util.*;

public class EndProgram implements Command {
    @Override
    public Output action(final Input input) {
        Map<String, Ranking> rankings = new HashMap<>();

        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == AppUtils.UserType.LISTENER) {
                Stack<File> songStack = new Stack<>();
                Listener listener = (Listener) user;

                for (File file : listener.getHistory()) {
                    if (file.getFileType() == AppUtils.FileType.AD) {
//                        System.out.println("Ad");
                        int adPrice = ((Ad) file).getPrice();
                        //int noPayedSongs = listener.getTotalListens();
                        int noPayedSongs = songStack.size();

                        while (!songStack.isEmpty()) {
                            Song song = (Song) songStack.pop();
                            Artist artist = (Artist) Database.getInstance()
                                .findUser(song.getArtist());
                            if (artist != null) {
                                //System.out.println(song.getName() + " " + adPrice + " "
                                  //  + noPayedSongs + " " + artist.getUsername());
                                artist.setSongRevenue(artist.getSongRevenue()
                                    + 1.0 * adPrice / noPayedSongs);
                            }
                        }
                    } else {
                        //System.out.println("Song " + file.getName() + " " + ((Song) file)
                        // .getArtist());
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

//                System.out.println(artist.getUsername());
                for (Map.Entry<Listener, Integer> listen : artist.getListens().entrySet()) {
                    //if (listen.getKey().isPremium()) {
                    artist.setSongRevenue(artist.getSongRevenue()
                        + 1000000.0 * listen.getValue() / listen.getKey().getPremiumListens());

                    for (Map.Entry<Song, Integer> songListen :
                        listen.getKey().getListensPerSong().entrySet()) {
                        if (!songListen.getKey().getArtist().equals(artist.getUsername())) {
                            continue;
                        }

                        if (!artist.getRevenuePerSong().containsKey(songListen.getKey())) {
                            artist.getRevenuePerSong().put(songListen.getKey(),
                                1000000.0 * songListen.getValue()
                                    / listen.getKey().getPremiumListens());
                        } else {
                            artist.getRevenuePerSong().put(songListen.getKey(),
                                artist.getRevenuePerSong().get(songListen.getKey())
                                    + 1000000.0 * songListen.getValue()
                                    / listen.getKey().getPremiumListens());
                        }
                    }

//                    System.out.println(listen.getValue() + " " + listen.getKey().getPremiumListens()
//                        + " = " + artist.getSongRevenue());
                    //}
                }

                Song mostProfitableSong = null;
                for (Map.Entry<Song, Double> songRevenue :
                    artist.getRevenuePerSong().entrySet()) {
//                    System.out.println(songRevenue.getKey().getName() + " "
//                        + songRevenue.getValue());
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

                artist.setSongRevenue(Math.round(artist.getSongRevenue() * 100.0) / 100.0);

                rankings.put(artist.getUsername(),
                    new Ranking(artist.getMerchRevenue(), artist.getSongRevenue(),
                        mostProfitableSong == null ? "N/A" : mostProfitableSong.getName()));
            }

//            if (user.getUserType() == AppUtils.UserType.HOST) {
//                Host host = (Host) user;
//                Host.HostTops hostTops = (Host.HostTops) user.getTops();
//
//                if (hostTops.getListeners() == 0) {
//                    continue;
//                }
//            }
        }

        rankings = sortMap(rankings);

        int rank = 1;
        for (Map.Entry<String, Ranking> entry : rankings.entrySet()) {
            Artist artist = (Artist) Database.getInstance().findUser(entry.getKey());
            Artist.ArtistTops artistTops = (Artist.ArtistTops) artist.getTops();

            entry.getValue().setRanking(rank++);
        }

        return new Output(input.getCommand(), rankings);
    }

    Map<String, Ranking> sortMap(Map<String, Ranking> unsortedMap) {
        List<Map.Entry<String, Ranking>> list = new LinkedList<>(unsortedMap.entrySet());

        list.sort((o1, o2) -> {
            if (o1.getValue().getMerchRevenue() + o1.getValue().getSongRevenue()
                == o2.getValue().getMerchRevenue() + o2.getValue().getSongRevenue()) {
                return o1.getKey().compareTo(o2.getKey());
            }

            return Double.compare(o2.getValue().getMerchRevenue() + o2.getValue().getSongRevenue()
                , o1.getValue().getMerchRevenue() + o1.getValue().getSongRevenue());
        });

        LinkedHashMap<String, Ranking> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Ranking> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
