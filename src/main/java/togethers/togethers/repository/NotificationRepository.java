package togethers.togethers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import togethers.togethers.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByUser_Id(Long id);

}
