package togethers.togethers.test;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderItem {

    @Id@GeneratedValue
    private Long orderitem_id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;
}
