package site.companycolor.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SYSTEM_USER")
@Data
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String password;

    @Column(name = "user_nm", nullable = false)
    private String name;

    @Column(name = "user_auth", nullable = false)
    private String authority;
}