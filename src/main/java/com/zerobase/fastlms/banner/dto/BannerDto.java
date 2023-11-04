package com.zerobase.fastlms.banner.dto;

import com.zerobase.fastlms.banner.entity.Banner;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BannerDto {

    private Long id;

    private String subject;

    private String filePath;

    private String link;

    private String target;

    private int orderIndex;

    private boolean showYn;

    private LocalDateTime regDt;

    @Setter
    private long totalCount;

    @Setter
    private long seq;

    public static BannerDto fromEntity(Banner entity) {
        return BannerDto.builder()
                .id(entity.getId())
                .subject(entity.getSubject())
                .filePath(entity.getFilePath())
                .link(entity.getLink())
                .target(entity.getTarget())
                .orderIndex(entity.getOrderIndex())
                .showYn(entity.isShowYn())
                .regDt(entity.getRegDt())
                .build();
    }
}
