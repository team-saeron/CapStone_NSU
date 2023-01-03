package togethers.togethers.test;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    private String createby;
    private LocalDateTime createDate;

    private String LastModifiedby;
    private LocalDateTime lastModifiedDate;
}
