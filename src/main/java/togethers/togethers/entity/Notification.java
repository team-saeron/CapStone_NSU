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

    private String message;



    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name="id")
    private User user;

    @Builder
    public Notification(String title, /*String link, boolean checked, LocalDateTime created,*/ String message, User user) {
        this.title = title;
        this.user=user;
        this.message = message;
    }

}