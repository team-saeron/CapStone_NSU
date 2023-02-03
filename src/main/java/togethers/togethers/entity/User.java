package togethers.togethers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import togethers.togethers.data.dto.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
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


    @Column(nullable = false)
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

    @Column(nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birth;

    @Column(length = 30,nullable = false)
    private String nickname;

    // 사용자가 좋아요를 눌른 값을 저장하고있는 DB와 연관관계 설정
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default()
    private List<Like>likes = new ArrayList<>();

    // 사용자가 댓글을 달은 DB와 연관관계 설정
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default()
    private List<Reply>replyes = new ArrayList<>();

    // 사용자가 작성한 게시물과 연관관계 설정
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne()
    @JoinColumn(name = "userDetail_id")
    private UserDetail userDetail;

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
