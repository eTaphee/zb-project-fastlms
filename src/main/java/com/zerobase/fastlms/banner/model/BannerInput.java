package com.zerobase.fastlms.banner.model;

import lombok.Data;

@Data
public class BannerInput {

    long id;
    String subject;
    String link;
    String target;
    int orderIndex;
    boolean showYn;

    String idList;
    String filename;
}
