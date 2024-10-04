package site.companycolor.demo.controller;

import site.companycolor.demo.dto.ApiResponse;
import site.companycolor.demo.dto.UserDto;
import site.companycolor.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> userList(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String name) {
        List<UserDto> users;
        if (userId != null || name != null) {
            users = userService.getUsersByIdAndName(userId, name);
        } else {
            users = userService.getAllUsers();
        }
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(ApiResponse.success(createdUser));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(ApiResponse.success(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(id));
    }
}