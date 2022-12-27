package togethers.togethers.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long sex_id;

    private int sex_value;

    private String member_id;
}
