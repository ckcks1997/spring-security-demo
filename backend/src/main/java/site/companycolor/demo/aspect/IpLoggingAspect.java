package site.companycolor.demo.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.companycolor.demo.entity.SystemUser;
import site.companycolor.demo.entity.UserHistory;
import site.companycolor.demo.repository.SystemUserRepository;
import site.companycolor.demo.service.UserHistoryService;
import site.companycolor.demo.util.IpUtil;

import java.util.Optional;

@Aspect
@Component
@Slf4j
public class IpLoggingAspect {

    @Autowired
    private UserHistoryService userHistoryService;

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Before("execution(* site.companycolor.demo.controller.UserController.*(..))")
    public void logIpAddress(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = IpUtil.getClientIpAddress(request);
        String methodName = joinPoint.getSignature().getName();

        UserHistory.ActionType actionType;
        if (methodName.startsWith("create")) {
            actionType = UserHistory.ActionType.C;
        } else if (methodName.startsWith("update")) {
            actionType = UserHistory.ActionType.U;
        } else if (methodName.startsWith("delete")) {
            actionType = UserHistory.ActionType.D;
        } else {
            return;
        }

        String userName = getUserIdFromRequest(request);
        Optional<SystemUser> user = systemUserRepository.findByUserId(userName);
        if (user.isPresent()) {
            userHistoryService.saveHistory(user.get().getId(), request.getRequestURI(), actionType, ipAddress);
        } else {
            log.error("User not found: {}", userName);
        }
    }

    private String getUserIdFromRequest(HttpServletRequest request) {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
