package commandmanager.input.commands;

import commandmanager.input.Input;
import commandmanager.output.Output;
import data.Database;
import data.entities.users.Artist;
import data.entities.Ranking;
import data.entities.users.User;
import utils.AppUtils;

import java.util.*;

public class EndProgram implements Command {
    @Override
    public Output action(final Input input) {
        Map<String, Ranking> rankings = new HashMap<>();

        for (User user : Database.getInstance().getUsers()) {
            if (user.getUserType() == AppUtils.UserType.ARTIST) {
                Artist artist = (Artist) user;
                Artist.ArtistTops artistTops = (Artist.ArtistTops) user.getTops();

                if (artistTops.getListeners() == 0) {
                    continue;
                }

                rankings.put(artist.getUsername(),
                        new Ranking(artist.getMerchRevenue(), artist.getSongRevenue()));
            }
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
