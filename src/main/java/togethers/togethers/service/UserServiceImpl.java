package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.data.dto.UserDetailSaveDto;
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


//    public SignInResultDto signIn(LoginRequestDto dto) throws RuntimeException{
//        User user = userRepository.findByUid(dto.getId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
//        if(!passwordEncoder.matches(dto.getPw(), user.getPassword())){
//            throw new RuntimeException();
//        }
//
//        SignInResultDto signInResultDto = SignInResultDto.builder().token(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles()))
//                .id(user.getId()).build();
//                return signInResultDto;
//    }

    @Override
    @Transactional(readOnly = false)
    public Long saveIntro(String name, UserDetailSaveDto userDetailSaveDto){
       User user = userRepository.getByName(name).orElse(null);

       UserDetail userDetail = UserDetail.createIntro(userDetailSaveDto.getNickname(),userDetailSaveDto.getRegions(),userDetailSaveDto.getMbti(),userDetailSaveDto.getWish_roommate(),
               userDetailSaveDto.getMonthly_fee(),userDetailSaveDto.getLease_fee(),userDetailSaveDto.getSex(),userDetailSaveDto.getPet(),userDetailSaveDto.getSmoking(),
               userDetailSaveDto.getLife_cycle(),user);
       userDetailRepository.save(userDetail);
       return userDetail.getId();
    }

//    @Transactional
//    public User findMember(String memberid)
//    {
//        User user = userRepository.getByUid(memberid);
//        return user;
//    }











}

