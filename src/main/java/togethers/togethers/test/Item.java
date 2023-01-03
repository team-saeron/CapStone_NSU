package togethers.togethers.test;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn
public class Item{

    @Id@GeneratedValue
    private Long item_id;

    private String name;

    private int price;


}
