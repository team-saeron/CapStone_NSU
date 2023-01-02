package togethers.togethers.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "member")
//회원 정보
public class Member implements Serializable {

    //length = varchar의 길이
    // nullable = false 는 notNull 제약조건
    @Id
    @Column(name="member_id",length = 30, nullable = false)
    private String id;

    private String loginId;

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

    @Column(nullable = false)
    private int age;

    // 사용자가 좋아요를 눌른 값을 저장하고있는 DB와 연관관계 설정
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Like>likes = new ArrayList<>();

    // 사용자가 댓글을 달은 DB와 연관관계 설정
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Reply>replyes = new ArrayList<>();

    // 사용자가 작성한 게시물과 연관관계 설정
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;




}
