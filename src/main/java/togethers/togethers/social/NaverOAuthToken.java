package togethers.togethers.social;

import lombok.Data;

@Data
public class NaverOAuthToken {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private String error;
    private String error_description;

    @Override
    public String toString() {
        return "NaverOAuthToken{" +
                "access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", error='" + error + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
