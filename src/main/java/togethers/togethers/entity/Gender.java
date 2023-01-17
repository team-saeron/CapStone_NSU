package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "gender")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long gender_id;

    private Integer gender_value;


    private String member_id;
}
