package togethers.togethers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindPasswordDto {
    private String name;
    private String email;
    private String id;

    public FindPasswordDto(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
}