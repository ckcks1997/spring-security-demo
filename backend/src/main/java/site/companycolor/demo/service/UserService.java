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
    private UserHistoryService userHistoryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public UserDto getUserByUserId(String id) {
        SystemUser user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
        SystemUser user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        userHistoryService.saveHistory(user.getId(), "CREATE", UserHistory.ActionType.C);
        return convertToDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDto.getName());
        user = userRepository.save(user);
        userHistoryService.saveHistory(user.getId(), "UPDATE", UserHistory.ActionType.U);
        return convertToDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        userHistoryService.saveHistory(id, "DELETE", UserHistory.ActionType.D);
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
        user.setAuthority("USER");
        return user;
    }
}