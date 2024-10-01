package site.companycolor.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER_HISTORY")
@Data
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_idx")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @Column(name = "reg_user_idx", nullable = false)
    private Long regUserId;

    @Column(name = "reg_ip", nullable = false)
    private String regIp;

    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    public enum ActionType {
        C, U, D
    }
}