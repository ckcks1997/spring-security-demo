package site.companycolor.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.companycolor.demo.dto.UserDto;
import site.companycolor.demo.entity.SystemUser;
import site.companycolor.demo.repository.SystemUserRepository;
import site.companycolor.demo.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Test
    public void 어드민계정_생성() {
        UserDto adminDto = new UserDto();
        adminDto.setUserId("admin");
        adminDto.setName("관리자계정1");
        adminDto.setPassword("password");
        adminDto.setAuthority("SYSTEM_ADMIN");
        UserDto createdAdmin = userService.createUser(adminDto);

        //생성 성공시 조회한 값이랑 동일한지 확인
        SystemUser savedAdmin = systemUserRepository.findByUserId("admin").orElse(null);
        assertNotNull(savedAdmin);
        assertEquals("관리자계정1", savedAdmin.getName());
        assertEquals("SYSTEM_ADMIN", savedAdmin.getAuthority());
    }

    @Test
    public void 일반계정_생성() {
        UserDto userDto = new UserDto();
        userDto.setUserId("user");
        userDto.setName("일반유저");
        userDto.setPassword("password");
        userDto.setAuthority("USER");
        UserDto createdUser = userService.createUser(userDto);

        //생성 성공시 조회한 값이랑 동일한지 확인
        SystemUser savedUser = systemUserRepository.findByUserId("user").orElse(null);
        assertNotNull(savedUser);
        assertEquals("일반유저", savedUser.getName());
        assertEquals("USER", savedUser.getAuthority());
    }
}