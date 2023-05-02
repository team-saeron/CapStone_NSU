package togethers.togethers.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class LifeCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifeCycle_id;

    private String lifeCycle_value;
}


/**
 * 아이디 비밀 번호 찾기 페이지
 * 게시물 등록 페이지 디자인
 **/