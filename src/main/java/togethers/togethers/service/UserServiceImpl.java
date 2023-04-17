package togethers.togethers.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import togethers.togethers.config.JwtTokenProvider;
import togethers.togethers.dto.*;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;
import togethers.togethers.repository.UserDetailRepository;
import togethers.togethers.repository.UserRepository;


@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserDetailRepository userDetailRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    @Transactional
    public String join(User user) {
        userRepository.save(user);
        return user.getUid();
    }


    @Override
    @Transactional(readOnly = false)
    public void saveIntro(String id, UserDetailSaveDto userDetailSaveDto) {

        log.info("[saveIntro] 유저 세부사항 저장 Service 로직 동작. userid :{}",id);
        User user = userRepository.findByUid(id).orElse(null);

        UserDetail userDetail = new UserDetail(userDetailSaveDto);
        userDetail.setUser(user);
        userDetailRepository.save(userDetail);

        user.setUserDetail(userDetail);
        userRepository.flush();

    }


    @Override
    @Transactional(readOnly = false)
    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto) {
        User user = userRepository.findByUserDetail_UserDetailId(id).orElse(null);
        UserDetail userDetail = userDetailRepository.findByUserDetailId(id).orElse(null);

        userDetail.updateUserDetail(userDetailUpdateDto);

        userDetailRepository.flush();

    }


    @Override
    @Transactional
    public User findIdByPhoneNum(FindIdPhoneDto findIdPhoneDto)
    {
        log.info("[findId] 아이디 핸드폰 번호로 Service 로직 동작. Name : {} ,  phoneNum : {}", findIdPhoneDto.getName(), findIdPhoneDto.getPhoneNum());
        User user = userRepository.findByNameAndPhoneNum(findIdPhoneDto.getName(), findIdPhoneDto.getPhoneNum()).orElse(null);
        return user;
    }

    @Override
    @Transactional
    public User findIdByEmail(FindIdEmailDto findIdEmailDto)
    {
        log.info("[findIdByEmail] 아이디 이메일로 찾기 Service 로직 동작. name: {}, email : {}",findIdEmailDto.getName(),findIdEmailDto.getEmail());
        User user = userRepository.findByNameAndEmail(findIdEmailDto.getName(), findIdEmailDto.getEmail()).orElse(null);
        return user;
    }

    @Override
    @Transactional
    public MailDto sendEmail(FindPassword findPassword){
        User user = userRepository.findByName(findPassword.getName()).orElse(null);
        if(user.getUid().equals(findPassword.getId())&&user.getName().equals(findPassword.getName())&&user.getEmail().equals(findPassword.getEmail())){
            log.info("[If문 findPassword] userPhone: {}, dtoPhone: {}", user.getPhoneNum());
            // Mail Server 설정
            String str = getTempPassword();
            MailDto mailDto = new MailDto();
            mailDto.setAddress(findPassword.getEmail());
            mailDto.setTitle("Together 임시비밀번호 안내 이메일 입니다.");
            mailDto.setMessage("안녕하세요. Together 임시비밀번호 안내 관련 이메일입니다."+" 회원님의 임시 비밀번호는 "+str+" 입니다. 로그인 후에 비밀번호를 변경을 해주세요." );
            user.setPassword(str);
            return mailDto;
        }else{
            log.info("[sendEmail] 사용자 정보 일치 하지 않음.");
            return null;
        }
    }


    @Override
    @Transactional
    public String getTempPassword(){
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str ="";

        int idx =0;
        for(int i =0; i<10;i++){
            idx = (int)(charSet.length*Math.random());
            str += charSet[idx];
        }
        return str;
    }

    @Override
    @Transactional
    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("songhyeon4643@gmail.com");
        message.setReplyTo("songhyeon4643@gmail.com");
        System.out.println("message"+message);
        mailSender.send(message);
    }

    @Override
    @Transactional
    public void updatePassword(Long memberId, String password){
        User user = userRepository.findById(memberId).orElse(null);
        user.setPassword(password);
    }

}