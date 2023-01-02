package togethers.togethers.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long pet_id;

    private int pet_value;
}
