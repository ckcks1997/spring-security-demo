package site.companycolor.demo.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userId;
    private String name;
    private String password;
    private String authority;
}