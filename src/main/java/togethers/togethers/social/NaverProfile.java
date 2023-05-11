package togethers.togethers.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class NaverProfile {

    private String resultcode;
    private String message;
    private Response response;
    @JsonIgnoreProperties(ignoreUnknown=true)
    @Data
    public class Response{
        private String id;
        private String nickname;
        private String name;
        private String email;
        private String gender;
        private String birthday;
        private String birthyear;
        private String profile_image;
        private String mobile;
        private String mobile_e164;

    }
}
