package togethers.togethers.service;

import togethers.togethers.dto.login.*;
import togethers.togethers.dto.mypage.UserDetailSaveDto;
import togethers.togethers.dto.mypage.UserDetailUpdateDto;
import togethers.togethers.entity.Post;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;

import java.util.HashSet;
import java.util.List;

public interface UserService {

    public String join(User user);
    public void saveIntro(String name, UserDetailSaveDto userDetailSaveDto);

    public void editIntro(Long id, UserDetailSaveDto userDetailSaveDto);

    public User findUserByIdAndPhoneNum(FindIdPhoneDto findIdPhoneDto);

    public User findUserByEmail(String email);

    public User findUserByEmail(FindIdEmailDto findIdEmailDto);

    public User findUserByUserDetailId(Long user_detail_id);

    public UserDetail findUserDetailByUserDetailId(Long UserDatailId);

    public MailDto sendEmail(FindPasswordDto findPasswordDto);

    public String getTempPassword();

    public User findPostByPostId(Long PostId);


    public void mailSend(MailDto mailDto);

    public boolean updatePassword(PasswordUpdatedDto passwordUpdatedDto);

    public HashSet<Post> matching(String userId);

    public int mathingPoint(Long userId, Long otherUserId);

}