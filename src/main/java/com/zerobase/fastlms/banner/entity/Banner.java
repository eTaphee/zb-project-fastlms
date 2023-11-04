package com.zerobase.fastlms.banner.entity;

import com.zerobase.fastlms.banner.model.BannerInput;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String filePath;

    private String link;

    private String target;

    private int orderIndex;

    private boolean showYn;

    @CreatedDate
    private LocalDateTime regDt;

    @LastModifiedDate
    private LocalDateTime udtDt;

    public void update(BannerInput input) {
        this.subject = input.getSubject();
        this.link = input.getLink();
        this.target = input.getTarget();
        this.orderIndex = input.getOrderIndex();
        this.showYn = input.isShowYn();

        if (input.getFilename() != null) {
            this.filePath = input.getFilename();
        }
    }

    public static Banner from(BannerInput input) {
        return Banner.builder()
                .subject(input.getSubject())
                .link(input.getLink())
                .filePath(input.getFilename())
                .target(input.getTarget())
                .orderIndex(input.getOrderIndex())
                .showYn(input.isShowYn())
                .build();
    }
}
