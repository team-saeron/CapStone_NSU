package togethers.togethers.service;

import togethers.togethers.dto.FindUserDto;
import togethers.togethers.dto.UserDetailUpdateDto;
import togethers.togethers.dto.UserDetailSaveDto;
import togethers.togethers.entity.User;

public interface UserService {

    public String join(User user);
    public Long saveIntro(String name, UserDetailSaveDto userDetailSaveDto);

    public void editIntro(Long id, UserDetailUpdateDto userDetailUpdateDto);

    public User findId(FindUserDto findUserDto);

}
