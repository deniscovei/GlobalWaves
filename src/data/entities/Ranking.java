package data.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ranking {
    double merchRevenue = 0;
    double songRevenue = 0;
    int ranking = 0;
    String mostProfitableSong = "N/A";

    public Ranking(final double merchRevenue, final double songRevenue,
                   final String mostProfitableSong) {
        this.merchRevenue = merchRevenue;
        this.songRevenue = songRevenue;
        this.mostProfitableSong = mostProfitableSong;
    }
}
