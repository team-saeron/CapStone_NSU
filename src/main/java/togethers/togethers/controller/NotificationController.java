package togethers.togethers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import togethers.togethers.entity.Notification;
import togethers.togethers.entity.User;
import togethers.togethers.repository.NotificationRepository;
import togethers.togethers.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public String getNotifications(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User)principal;
        List<Notification> notifications = notificationRepository.findAllByUser_Id(user.getId());
//        putCategorizedNotifications(model, notifications, numberOfChecked, notifications.size());
//        model.addAttribute("isNew", true);
//        notificationService.markAsRead(notifications);
        log.info("@@@@@@@@@@@@@@@@@{},{},{}",notifications.get(0).getTitle(),notifications.get(0).getMessage(),notifications.get(1).getTitle());
        return "redirect:/";
    }

//    @GetMapping("/notifications/old")
//    public String getOldNotifications(Model model) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = (User)principal;
//        List<Notification> notifications = notificationRepository.findByAccountAndCheckedOrderByCreatedDesc(user, true);
//        long numberOfNotChecked = notificationRepository.countByAccountAndChecked(user, false);
//        putCategorizedNotifications(model, notifications, notifications.size(), numberOfNotChecked);
//        model.addAttribute("isNew", false);
//        return "notification/list";
//    }

//    @DeleteMapping("/notifications")
//    public String deleteNotifications() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = (User)principal;
//        notificationRepository.deleteBy(user, true);
//        return "redirect:/notifications";
//    }

//    private void putCategorizedNotifications(Model model, List<Notification> notifications, long numberOfChecked, long numberOfNotChecked) {
//        ArrayList<Notification> newStudyNotifications = new ArrayList<>();
//        ArrayList<Notification> eventEnrollmentNotifications = new ArrayList<>();
//        ArrayList<Notification> watchingStudyNotifications = new ArrayList<>();
//        for (Notification notification : notifications) {
//            switch (notification.getNotificationType()) {
//                case STUDY_CREATED: {
//                    newStudyNotifications.add(notification);
//                    break;
//                }
//                case EVENT_ENROLLMENT: {
//                    eventEnrollmentNotifications.add(notification);
//                    break;
//                }
//                case STUDY_UPDATED: {
//                    watchingStudyNotifications.add(notification);
//                    break;
//                }
//            }
//        }
//        model.addAttribute("numberOfNotChecked", numberOfNotChecked);
//        model.addAttribute("numberOfChecked", numberOfChecked);
//        model.addAttribute("notifications", notifications);
//        model.addAttribute("newStudyNotifications", newStudyNotifications);
//        model.addAttribute("eventEnrollmentNotifications", eventEnrollmentNotifications);
//        model.addAttribute("watchingStudyNotifications", watchingStudyNotifications);
//    }
}