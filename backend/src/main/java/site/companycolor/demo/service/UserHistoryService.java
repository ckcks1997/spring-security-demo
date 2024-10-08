package site.companycolor.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.companycolor.demo.entity.UserHistory;
import site.companycolor.demo.repository.UserHistoryRepository;

import java.time.LocalDateTime;

@Service
public class UserHistoryService {

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    public void saveHistory(Long userId, String url, UserHistory.ActionType actionType, String ipAddress) {
        UserHistory history = new UserHistory();
        history.setRegUserId(userId);
        history.setUrl(url);
        history.setActionType(actionType);
        history.setRegIp(ipAddress);
        history.setRegDt(LocalDateTime.now());

        userHistoryRepository.save(history);
    }

}