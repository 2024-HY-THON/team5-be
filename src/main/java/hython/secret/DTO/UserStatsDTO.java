package hython.secret.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatsDTO {
    private int belogcount;
    private long totalLikes;
    private int awardcount;

    public UserStatsDTO(int belogcount, long totalLikes, int awardcount) {
        this.belogcount = belogcount;
        this.totalLikes = totalLikes;
        this.awardcount = awardcount;
    }

}
