package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LifeCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long lifeCycle_id;

    private int lifeCycle_value;
}
