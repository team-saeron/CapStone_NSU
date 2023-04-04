package togethers.togethers.service;

import togethers.togethers.dto.*;
import togethers.togethers.entity.User;

public interface UserService {

    public String join(User user);
    public Long saveIntro(String name, UserDetailSaveDto userDetailSaveDto);

    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto);

    public User findId(FindUserDto findUserDto);

    public MailDto sendEmail(FindPassword findPassword);

    public String getTempPassword() ;


    public void mailSend(MailDto mailDto);

    public void updatePassword(Long id, String password);

}