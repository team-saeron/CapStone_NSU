package togethers.togethers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindUserDto {
    String name;
    String phoneNum;

    public FindUserDto(String name, String phoneNum){
        this.name=name;
        this.phoneNum=phoneNum;
    }
}