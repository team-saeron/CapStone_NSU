package togethers.togethers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import togethers.togethers.dto.MyPageUpdateDto;
import togethers.togethers.dto.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
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
    private Long id;

    @Column(nullable=false, unique=true)
    private String uid; // hbj1025 = em


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
    private String email; //실제쓰는이메미일

//    @Temporal(TemporalType.TIMESTAMP)
    private Date birth;

    @Column(length = 30,nullable = false)
    private String nickname;

    // 사용자가 좋아요를 눌른 값을 저장하고있는 DB와 연관관계 설정
//    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @Builder.Default()
//    private List<Like>likes = new ArrayList<>();

    // 사용자가 댓글을 달은 DB와 연관관계 설정
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Builder.Default()
    private List<Reply>replyes = new ArrayList<>();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Builder.Default()
    private List<Like>likes = new ArrayList<>();


    // 사용자가 작성한 게시물과 연관관계 설정
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;


    @OneToOne
    @JoinColumn(name = "userDetail_id")
    private UserDetail userDetail;

    @ElementCollection(fetch=FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){ //계정이 가지고 있는 권한 목록 리턴
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername(){
        return this.uid;
    } // 계정의 이름(=아이디)을 리턴

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }//계정이 만료됐는지 리턴(true면 만료x)

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }//계정이 잠겨있는지 리턴(true면 잠김x)
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    } //비밀번호가 만료됐는지 리턴(true면 만료x)

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    } //계정이 활성화 되어있는지 리턴(true 활성o)

    @Builder
    public User(MyPageUpdateDto myPageUpdateDto){
        this.password = myPageUpdateDto.getPassword();
    }

}
