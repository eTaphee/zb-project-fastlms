package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    public List<LoginHistory> findAllByMemberUserId(String userId);
}
