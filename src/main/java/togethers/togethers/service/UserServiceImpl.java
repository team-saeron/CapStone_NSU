package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.dto.UserDetailUpdateDto;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.repository.UserDetailRepository;
import togethers.togethers.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserDetailRepository userDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;



    @Override
    @Transactional
    public String join(User user){
        userRepository.save(user);
        return user.getUid();
    }




    @Override
    @Transactional(readOnly = false)
    public Long saveIntro(String id, UserDetailSaveDto userDetailSaveDto){
       User user = userRepository.getByUid(id).orElse(null);

       UserDetail userDetail = UserDetail.createIntro(userDetailSaveDto.getNickname(),userDetailSaveDto.getRegions(),userDetailSaveDto.getMbti(),userDetailSaveDto.getWish_roommate(),
               userDetailSaveDto.getMonthly_fee(),userDetailSaveDto.getLease_fee(),userDetailSaveDto.getGender(),userDetailSaveDto.getPet(),userDetailSaveDto.getSmoking(),
               userDetailSaveDto.getLife_cycle());

       user.setUserDetail(userDetail);
       userRepository.save(user);

       userDetailRepository.save(userDetail);
       return userDetail.getUserDetailId();
    }


    @Override
    @Transactional(readOnly = false)
    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto){
        User user = userRepository.findByUserDetail_UserDetailId(id).orElse(null);
        UserDetail userDetail = userDetailRepository.findByUserDetailId(id).orElse(null);

        userDetail.updateUserDetail(userDetailUpdateDto);

        userDetailRepository.flush();

    }








}

