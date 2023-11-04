package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerDto getById(Long id) {
        return bannerRepository.findById(id).map(BannerDto::fromEntity).orElse(null);
    }

    @Transactional
    public boolean set(BannerInput parameter) {
        Banner banner = bannerRepository.findById(parameter.getId()).orElse(null);
        if (banner == null) {
            return false;
        }

        banner.update(parameter);
        return true;
    }

    @Transactional
    public boolean add(BannerInput parameter) {
        bannerRepository.save(Banner.from(parameter));
        return true;
    }

    @Transactional(readOnly = true)
    public List<BannerDto> list(BannerParam parameter) {
        List<BannerDto> bannerList = bannerRepository.findAll()
                .stream()
                .map(BannerDto::fromEntity)
                .collect(Collectors.toList());
        long totalCount = bannerList.size();

        for (int i = 0; i < totalCount; i++) {
            bannerList.get(i).setTotalCount(totalCount);
            bannerList.get(i).setSeq(totalCount - parameter.getPageStart() - i);
        }

        return bannerList;
    }

    @Transactional
    public boolean del(String idList) {
        if (idList != null && !idList.isEmpty()) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception ignored) {
                }

                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }

        return true;
    }
}
