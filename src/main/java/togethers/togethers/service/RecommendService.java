package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.dto.recommend.RecommendUserDto;
import togethers.togethers.entity.Mbti;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.repository.MbtiRepository;
import togethers.togethers.repository.PostRepository;
import togethers.togethers.repository.UserDetailRepository;
import togethers.togethers.repository.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final MbtiRepository mbtiRepository;
    private final PostRepository postRepository;


    @Transactional
    public HashSet<Post> matching(String uid) {
        User user = userRepository.findByUid(uid).orElse(null);
        UserDetail ud = userDetailRepository.findById(user.getUserDetail().getUserDetailId()).orElse(null);
        List<UserDetail> sameGenderList = userDetailRepository.findAllByGender(ud.getGender()); // 성별 필터
        List<UserDetail> sameMbtiList = new ArrayList<>();
        Mbti userMbti = mbtiRepository.findByMbti(ud.getMbti()).orElse(null);
        HashSet<Post>recommendPost = new HashSet<>();


        log.info("[matching] 매칭 알고리즘 시작. userID:{}, userMbti :{}",uid,ud.getMbti());


        for (UserDetail i : sameGenderList)
        {
            if (i.getMbti().equals(userMbti.getFirstMbti()) || i.getMbti().equals(userMbti.getSecondMbti()) || i.getMbti().equals(userMbti.getThirdMbti()) || i.getMbti().equals(userMbti.getFourthMbti()))
            {
                sameMbtiList.add(i);
            }
            else
            {
                continue;
            }
        }

        List<RecommendUserDto> recommendUserDtos = sortRecommendUserList(user, sameMbtiList);

        for (RecommendUserDto recommendUserDto : recommendUserDtos) {
            User temp_user = recommendUserDto.getUser();
            if (temp_user==null || temp_user.getPost()==null)
            {
                continue;
            }else {
                Post post = postRepository.findBypostId(temp_user.getPost().getPostId()).orElse(null);
                recommendPost.add(post);
                if (recommendPost.size()==4)
                {
                    break;
                }
            }
        }

        while(recommendPost.size() < 4)
        {
            log.info("[matching] recommend_post의 갯수가 4개이하여서 추가 등록 로직 동작");
            for (UserDetail userDetail : sameGenderList) {
                User sameGenderUser = userRepository.findByUserDetail_UserDetailId(userDetail.getUserDetailId()).orElse(null);
                log.info("[matching] same_gender_user id :{}",sameGenderUser);

                if(sameGenderUser == null || sameGenderUser.getPost() == null || user.getId() == sameGenderUser.getId())
                {
                    continue;
                }else {
                    Post post = postRepository.findBypostId(sameGenderUser.getPost().getPostId()).orElse(null);
                    log.info("[matching] 추천 게시물에 추가할 게시물 ok : {}",post.getPostId());
                    recommendPost.add(post);
                }
            }
        }
        return recommendPost;
    }

    public int mathingPoint(Long userId, Long otherUserId)
    {
        int mathingScore = 0;
        UserDetail userDetail = userRepository.findById(userId).orElse(null).getUserDetail();
        UserDetail otherUserDetail = userRepository.findById(otherUserId).orElse(null).getUserDetail();

        String myLifeCycle = userDetail.getLife_cycle();
        String otherLifeCycle = otherUserDetail.getLife_cycle();

        if(myLifeCycle.equals(otherLifeCycle))
        {
            mathingScore += 30;
        }else
        {
            mathingScore += 15;
        }

        String mySmoking = userDetail.getSmoking();
        String otherSmoking = otherUserDetail.getSmoking();

        if(mySmoking.equals(otherSmoking))
        {
            mathingScore+=40;
        }else if(mySmoking.equals("싫어요") && otherSmoking.equals("좋아요"))
        {
            mathingScore += 0;
        }else {
            mathingScore += 20;
        }

        String myPet = userDetail.getPet();
        String otherPet = otherUserDetail.getPet();

        if(myPet.equals(otherPet))
        {
            mathingScore+=30;
        }else if(myPet.equals("싫어요") && otherPet.equals("좋아요"))
        {
            mathingScore += 0;
        }else {
            mathingScore += 15;
        }

        log.info("[mathingScore] {}의 유저와 {} 유저의 매칭 점수 결과 : {}",userId,otherUserId,mathingScore);

        return mathingScore;
    }



    public List<RecommendUserDto>sortRecommendUserList(User user, List<UserDetail>sameMbtiList)
    {
        log.info("[sortRecommendUserList] 같은 유사도가 높은 mbti list 에서 matchingScore 점수 산정 서비스 로직 시작");
        List<RecommendUserDto>recommendUserList = new ArrayList<>();
        for (UserDetail x : sameMbtiList) {
            User temp_user = userRepository.findByUserDetail_UserDetailId(x.getUserDetailId()).orElse(null);
            int mathingScore = mathingPoint(user.getId(), temp_user.getId());
            RecommendUserDto recommendUserDto = RecommendUserDto.builder()
                    .user(temp_user)
                    .matchingScore(mathingScore)
                    .build();
            recommendUserList.add(recommendUserDto);
        }

        Collections.sort(recommendUserList);
        for (RecommendUserDto recommendUserDto : recommendUserList) {
            log.info("마몽 : 다른 사람 id :{} 매칭 점수 : {}",recommendUserDto.getUser().getId(),recommendUserDto.getMatchingScore());
        }


        return recommendUserList;

    }
}
