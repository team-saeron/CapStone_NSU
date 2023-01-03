package togethers.togethers.test;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Delivery {

    @Id@GeneratedValue
    private Long delivery_id;

    private String street;
    private String city;
    private String zipcode;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Status DeliveryStatus;
}
