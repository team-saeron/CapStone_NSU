package togethers.togethers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindPassword {
    String name;
    String email;
    String id;
}