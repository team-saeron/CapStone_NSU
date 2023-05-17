package togethers.togethers.dto.post;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MonthlyPostRequestDto {

    private String title;
    private String context;
    private String areaName;
    private String monthly;
    private String deposit;
    private String managementFee;

}
