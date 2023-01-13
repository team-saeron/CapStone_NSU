package togethers.togethers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@Builder
@AllArgsConstructor
@Table(name = "member")
//회원 정보
public class Member implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id", length = 50 ,nullable = false, unique = true)
    private String id;


    @Column(name="member_pw", length = 30,nullable = false)
    @NotEmpty(message="비밀번호를 입력해주세요.")
    private String password;

    @Column(name="member_name", length = 30, nullable = false)
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Column(name="phone_num", length = 20, nullable = false)
    private String phoneNum;

    @Column(length = 30,nullable = false)
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birth;

    @Column(length = 30,nullable = false)
    private String nickname;

//    @Column(nullable = false)
//    private int age;


//    // 사용자가 좋아요를 눌른 값을 저장하고있는 DB와 연관관계 설정
//
//
//
//
//
//
//    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
//    private List<Like>likes = new ArrayList<>();
//
//
//
//
//    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
//    private MemberDetail memberDetail;
//
//
//
//    // 사용자가 댓글을 달은 DB와 연관관계 설정
//    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
//    private List<Reply>replyes = new ArrayList<>();





    // 사용자가 작성한 게시물과 연관관계 설정
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @ElementCollection(fetch=FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access=Access.WRITE_ONLY)
    @Override
    public String getUsername(){
        return this.id;
    }

    @JsonProperty(access=Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpried(){
        return true;
    }

    @JsonProperty(access=Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @JsonProperty(access=Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpried(){
        return true;
    }

    @JsonProperty(access=Access.WRITE_ONLY)
    @Override
    public boolean isEnabled(){
        return true;
    }

}
