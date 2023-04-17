package togethers.togethers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FindIdEmailDto {
    private String name;
    private String email;

    public FindIdEmailDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
