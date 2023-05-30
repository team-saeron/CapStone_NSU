package togethers.togethers.dto.recommend;

import lombok.Builder;
import lombok.Data;
import togethers.togethers.dto.post.RecommendPostDto;
import togethers.togethers.entity.User;

@Data
@Builder
public class RecommendUserDto implements Comparable<RecommendUserDto>{
    private User user;
    private int matchingScore;

    @Override
    public int compareTo(RecommendUserDto o) {
        if(this.matchingScore > o.getMatchingScore())
        {
            return -1;
        }else if(this.matchingScore==o.matchingScore)
        {
            return 0;
        }else {
            return 1;
        }
    }
}
