package togethers.togethers.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Mbti {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    String mbti;

    String firstMbti;

    String secondMbti;

    String thirdMbti;

    String fourthMbti;

}
