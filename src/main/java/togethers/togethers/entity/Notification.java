package togethers.togethers.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String title;

//    private String link;

    private String message;

   // private boolean checked;


    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name="id")
    private User user;

    @Builder
    public Notification(String title, /*String link, boolean checked, LocalDateTime created,*/ String message, User user) {
        this.title = title;
        this.user=user;
        //notification.link = link;
       // notification.checked = checked;
        //notification.created = created;
        this.message = message;
    }

//    public void read() {
//        this.checked = true;
//    }
}
