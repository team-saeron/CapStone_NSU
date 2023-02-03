package togethers.togethers.service;

import togethers.togethers.dto.UserDetailSaveDto;

public interface UserService {
    public Long saveIntro(String name, UserDetailSaveDto userDetailSaveDto);
}
