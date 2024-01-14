package data.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Ranking {
    private double merchRevenue = 0;
    private double songRevenue = 0;
    private int ranking = 0;
    private String mostProfitableSong = "N/A";

    public Ranking(final double merchRevenue, final double songRevenue,
                   final String mostProfitableSong) {
        this.merchRevenue = merchRevenue;
        this.songRevenue = songRevenue;
        this.mostProfitableSong = mostProfitableSong;
    }
}
