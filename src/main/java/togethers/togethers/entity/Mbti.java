package togethers.togethers.entity;

import lombok.*;

import javax.persistence.*;

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

    String myMbti;

    String firstMbti;

    String secondMbti;

    String thirdMbti;

    String fourthMbti;

}
