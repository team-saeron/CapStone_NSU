package togethers.togethers.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import togethers.togethers.entity.Reply;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Builder
public class DetailPostDto {

    private String title;
    private String context;
    private Integer roomPay_type;

    private String photo_name;
    private String photo_path;

    @Builder.Default
    private List<Reply> replies = new ArrayList<>();
}
