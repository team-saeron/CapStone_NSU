package togethers.togethers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import togethers.togethers.data.dto.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table
//회원 정보
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, unique=true)
    private String uid;


    @Column(length = 30,nullable = false)
    @NotEmpty(message="비밀번호를 입력해주세요.")
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 30, nullable = false)
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Column(length = 20, nullable = false)
    private String phoneNum;

    @Column(length = 30,nullable = false)
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birth;

    @Column(length = 30,nullable = false)
    private String nickname;

    @ElementCollection(fetch=FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername(){
        return this.uid;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    }

}
