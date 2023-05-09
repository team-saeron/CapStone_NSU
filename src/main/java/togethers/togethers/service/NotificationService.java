package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.entity.Notification;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
//    public void markAsRead(List<Notification> notifications) {
//        notifications.forEach(Notification::read);
//    }
}