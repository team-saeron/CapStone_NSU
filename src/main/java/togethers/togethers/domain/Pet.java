package togethers.togethers.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Pet {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pet_id;

    private int pet_value;
}
