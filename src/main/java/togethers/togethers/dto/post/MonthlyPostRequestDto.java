package togethers.togethers.dto.post;


import lombok.Data;


@Data
public class MonthlyPostRequestDto {

    private String title;
    private String context;
    private String areaName;
    private String monthly;
    private String deposit;
    private String managementFee;

}
