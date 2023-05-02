package togethers.togethers.service;

import togethers.togethers.dto.login.*;
import togethers.togethers.dto.mypage.UserDetailSaveDto;
import togethers.togethers.dto.mypage.UserDetailUpdateDto;
import togethers.togethers.entity.User;
import togethers.togethers.entity.UserDetail;

public interface UserService {

    public String join(User user);
    public void saveIntro(String name, UserDetailSaveDto userDetailSaveDto);

    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto);

    public User findIdByPhoneNum(FindIdPhoneDto findIdPhoneDto);

    public User findIdByEmail(FindIdEmailDto findIdEmailDto);

    public UserDetail findUserDetail(Long UserDatailId);

    public MailDto sendEmail(FindPasswordDto findPasswordDto);

    public String getTempPassword() ;


    public void mailSend(MailDto mailDto);

    public boolean updatePassword(PasswordUpdatedDto passwordUpdatedDto);

}