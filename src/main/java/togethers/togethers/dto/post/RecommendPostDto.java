package togethers.togethers.dto.post;


import lombok.Getter;
import togethers.togethers.entity.Post;

@Getter
public class RecommendPostDto {

    private Long postId;

    private String filename;

    private String subject;

    private String explain;


    public RecommendPostDto(Post post)
    {
        this.postId = post.getPostId();

        this.filename = post.getFileName();

        this.subject = post.getTitle();

        if(post.getRoomPay_type() ==0)
        {
            String temp  = "월세 " + post.getDeposit() +"/"+post.getMonthly();
            this.explain = temp;
        }else{
            String temp  = "전세 " + post.getDeposit() +"/"+post.getManagement_fee();
            this.explain = temp;
        }
    }

}
