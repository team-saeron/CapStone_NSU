package togethers.togethers.dto.post;


import lombok.Data;

@Data
public class LeasePostRequestDto {

    private String title;
    private String context;
    private String deposit;
    private String managementFee;
    private String area;

}
