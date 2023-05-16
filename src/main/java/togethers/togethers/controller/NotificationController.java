package togethers.togethers.controller;

import groovy.util.logging.Slf4j;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import togethers.togethers.entity.Notification;
import togethers.togethers.entity.User;
import togethers.togethers.repository.NotificationRepository;
import togethers.togethers.service.NotificationService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    private final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @GetMapping("/notifications")
    public String getNotifications(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) principal;
        List<Notification> notifications = notificationRepository.findAllByUser_Id(user.getId());
        log.info("@@@@@@@@@@@@@@@@@{},{},{}", notifications.get(0).getTitle(), notifications.get(0).getMessage(), notifications.get(1).getTitle());
        return "redirect:/";
    }
}
