package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.entity.Notification;
import togethers.togethers.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Transactional
    public List<Notification>findNotification(Long userId)
    {
        List<Notification> notifications = notificationRepository.findAllByUser_Id(userId);
        logger.info("[findNotification] 사용자 게시물에 달린 댓글, 좋아요 조회 Service 로직 동작. userId :{} 조회 갯수 :{}",userId,notifications.size());

        return notifications;
    }
}
