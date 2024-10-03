package site.companycolor.demo.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.companycolor.demo.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByUserId(String userId);

    @Query("SELECT u " +
            "FROM SystemUser u " +
            "WHERE (:userId is null or u.userId like %:userId%) " +
            "AND (:userName is null or u.name like %:userName%)")
    List<SystemUser> findByUserIdAndName(@Param("userId") String userId, @Param("userName") String userName);
}

