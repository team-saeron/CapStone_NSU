package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.repository.UserDetailRepository;
import togethers.togethers.repository.UserRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserDetailRepository userDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String join(User user){
        userRepository.save(user);
        return user.getUid();
    }




    @Override
    @Transactional(readOnly = false)
    public Long saveIntro(String id, UserDetailSaveDto userDetailSaveDto){
       User user = userRepository.findByUid(id).orElse(null);

       UserDetail userDetail = UserDetail.createIntro(userDetailSaveDto.getNickname(),userDetailSaveDto.getRegions(),userDetailSaveDto.getMbti(),userDetailSaveDto.getWish_roommate(),
               userDetailSaveDto.getMonthly_fee(),userDetailSaveDto.getLease_fee(),userDetailSaveDto.getGender(),userDetailSaveDto.getPet(),userDetailSaveDto.getSmoking(),
               userDetailSaveDto.getLife_cycle());

       user.setUserDetail(userDetail);
       userRepository.save(user);

       userDetailRepository.save(userDetail);
       return userDetail.getUserDetail_id();
    }













}

