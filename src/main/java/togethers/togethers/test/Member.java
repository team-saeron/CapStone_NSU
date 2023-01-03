package togethers.togethers.test;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "test_member")
@Table(name = "test_member")
@Data
public class Member extends BaseEntity{

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Order>orders = new ArrayList<>();

    private String name;
    private String street;
    private String Zipcode;

}
