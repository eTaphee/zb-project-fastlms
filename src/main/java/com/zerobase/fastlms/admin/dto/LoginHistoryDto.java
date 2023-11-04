package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginHistoryDto {

    private final Long id;
    private final String userId;
    private final String ip;
    private final String userAgent;
    private final LocalDateTime loginAt;

    public static LoginHistoryDto fromEntity(LoginHistory entity) {
        return LoginHistoryDto.builder()
                .id(entity.getId())
                .userId(entity.getMember().getUserId())
                .ip(entity.getIp())
                .userAgent(entity.getUserAgent())
                .loginAt(entity.getLoginAt())
                .build();
    }
}
