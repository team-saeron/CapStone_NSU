package togethers.togethers.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    private Long seq;

    @Column(name="member_id")
    private Long email;

    @NotEmpty
    private String name;
    @NotEmpty
    private String password;

    private String phoneNum;

    private Date birth;



}
