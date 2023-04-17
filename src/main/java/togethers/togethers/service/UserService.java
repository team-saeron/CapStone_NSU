package togethers.togethers.service;

import togethers.togethers.dto.*;
import togethers.togethers.entity.User;

public interface UserService {

    public String join(User user);
    public void saveIntro(String name, UserDetailSaveDto userDetailSaveDto);

    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto);

    public User findIdByPhoneNum(FindIdPhoneDto findIdPhoneDto);

    public User findIdByEmail(FindIdEmailDto findIdEmailDto);

    public MailDto sendEmail(FindPasswordDto findPasswordDto);

    public String getTempPassword() ;


    public void mailSend(MailDto mailDto);

    public void updatePassword(Long id, String password);

}