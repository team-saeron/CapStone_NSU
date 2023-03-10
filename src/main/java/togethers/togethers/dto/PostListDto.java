package togethers.togethers.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import togethers.togethers.entity.Post;

@Data
@Builder
public class PostListDto {

    Page<Post> PostList;



}
