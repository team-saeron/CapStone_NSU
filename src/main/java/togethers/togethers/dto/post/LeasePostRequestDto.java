package togethers.togethers.dto.post;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeasePostRequestDto {

    private String title;
    private String context;
    private String deposit;
    private String managementFee;
    private String area;

}
