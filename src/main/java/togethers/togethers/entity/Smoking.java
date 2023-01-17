package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Smoking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smoking_id;

    private int smoking_value;
}