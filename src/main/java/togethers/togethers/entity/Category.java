package togethers.togethers.entity;




import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Category {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long area_id;

    @Column(length = 50, nullable = false)
    private String area;

}