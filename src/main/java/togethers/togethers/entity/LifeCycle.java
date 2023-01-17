package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class LifeCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifeCycle_id;

    private int lifeCycle_value;
}
