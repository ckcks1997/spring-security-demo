package site.companycolor.demo.service;

import site.companycolor.demo.dto.UserDto;
import site.companycolor.demo.entity.SystemUser;
import site.companycolor.demo.entity.UserHistory;
import site.companycolor.demo.repository.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private SystemUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User를 찾지 못했습니다."));
        return convertToDto(user);
    }

    public UserDto getUserByUserId(String id) {
        SystemUser user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User를 찾지 못했습니다."));
        return convertToDto(user);
    }

    public List<UserDto> getUsersByIdAndName(String userId, String userName) {
        List<SystemUser> users = userRepository.findByUserIdAndName(userId, userName);
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByUserId(userDto.getUserId()).isPresent()) {
            throw new RuntimeException("User ID가 이미 존재합니다.");
        }
        SystemUser user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return convertToDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User를 찾지 못했습니다."));
        user.setName(userDto.getName());
        user = userRepository.save(user);
        return convertToDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDto(SystemUser user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setAuthority(user.getAuthority());
        return dto;
    }

    private SystemUser convertToEntity(UserDto dto) {
        SystemUser user = new SystemUser();
        user.setUserId(dto.getUserId());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setAuthority(dto.getAuthority()!= null ? dto.getAuthority() : "USER"); // authority 없을경우 기본 USER 설정
        return user;
    }
}