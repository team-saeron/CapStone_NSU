package togethers.togethers.service;

import togethers.togethers.dto.login.*;
import togethers.togethers.dto.mypage.UserDetailSaveDto;
import togethers.togethers.dto.mypage.UserDetailUpdateDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public String join(User user);
    public void saveIntro(String name, UserDetailSaveDto userDetailSaveDto);

    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto);

    public User findIdByPhoneNum(FindIdPhoneDto findIdPhoneDto);

    public User findUserByEmail(String email);

    public User findIdByEmail(FindIdEmailDto findIdEmailDto);

    public UserDetail findByUserDetailId(Long UserDatailId);

    public MailDto sendEmail(FindPasswordDto findPasswordDto);

    public String getTempPassword();

    public User findByPostId(Long PostId);


    public void mailSend(MailDto mailDto);

    public boolean updatePassword(PasswordUpdatedDto passwordUpdatedDto);

    public List<Post> matching(String userId);

}