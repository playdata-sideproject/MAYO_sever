package kr.pe.mayo.service;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository dao;

    /** 자동 회원가입 후 추가정보 입력 */
    public User registerCreator(PrincipalDetails principalDetails, UserDTO.Register register) {
        // 시큐리티 세션에 저장된 user 정보 불러오는 코드 - 사용자정의 어노테이션으로 만들면 좋을듯
        User user = dao.findByUsername(principalDetails.getUser().getUsername());
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setPhone(register.getPhone());
        user.setBirth(register.getBirth());
        user.setSchool(register.getSchool());
        user.setRegCompleted(true);
        user.setUserStatus(true);
        dao.save(user);

        return user;
    }

    public User updateUser(PrincipalDetails principalDetails, UserDTO.Update update) {
        User user = dao.findByUsername(principalDetails.getUser().getUsername());
        user.setName(update.getName());
        user.setEmail(update.getEmail());
        user.setPhone(update.getPhone());
        user.setBirth(update.getBirth());
        user.setSchool(update.getSchool());
        dao.save(user);

        return user;
    }

    public void userWithdrawal(User user) {
        user.setUserStatus(false);
        dao.save(user);
    }

    public UserDTO.getInfo myInfo(Long userIdx) {
        User user = dao.findById(userIdx).get();
        return UserDTO.getInfo.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birth(user.getBirth())
                .school(user.getSchool()).build();
    }

    public List<UserDTO.getBasicInfo> getyUserByName(String name) {
        List<UserDTO.getBasicInfo> userList;
        userList = new ArrayList<UserDTO.getBasicInfo>();

        List<User> users = dao.findByName(name);
        users.forEach(user -> {
            userList.add(
                    new UserDTO.getBasicInfo().builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .build()
            );
        });
        return userList;
    }
}
