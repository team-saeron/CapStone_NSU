package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.dto.mypage.NotificationDto;
import togethers.togethers.entity.Notification;
import togethers.togethers.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Transactional
    public List<NotificationDto>findNotification(Long userId)
    {
        List<Notification> notifications = notificationRepository.findAllByUser_Id(userId);
        List<NotificationDto>notificationDtoList = new ArrayList<>();

        logger.info("[findNotification] 사용자 게시물에 달린 댓글, 좋아요 조회 Service 로직 동작. userId :{} 조회 갯수 :{}",userId,notifications.size());
        for (Notification notification : notifications) {
            NotificationDto notificationDto = NotificationDto.builder()
                    .message(notification.getMessage())
                    .build();
            notificationDtoList.add(notificationDto);
            if(notificationDtoList.size()==3)
            {
                break;
            }
        }
        return notificationDtoList;
    }
}
