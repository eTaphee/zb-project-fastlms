package com.zerobase.fastlms.banner.controller;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {
    private static final String REDIRECT_BANNER_LIST_DO = "redirect:/admin/banner/list.do";

    private final BannerService bannerService;



    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter) {

        parameter.init();

        List<BannerDto> bannerList = bannerService.list(parameter);

        long totalCount = bannerList.size();
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", bannerList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }

    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(Model model, HttpServletRequest request
            , CourseInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit.do");

        BannerDto detail = !editMode ? BannerDto.builder().build() : null;

        if (editMode) {
            BannerDto existBanner = bannerService.getById(parameter.getId());
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "배너정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existBanner;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/banner/add";
    }

    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request
            , MultipartFile file
            , BannerInput parameter) {

        if (file != null && file.getSize() > 0) {
            parameter.setFilename(saveFile(file));
        }

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode) {
            BannerDto existBanner = bannerService.getById(parameter.getId());
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "배너정보가 존재하지 않습니다.");
                return "common/error";
            }

            bannerService.set(parameter);
        } else {
            bannerService.add(parameter);
        }

        return REDIRECT_BANNER_LIST_DO;
    }

    @PostMapping("/admin/banner/delete.do")
    public String del(Model model, HttpServletRequest request
            , BannerInput parameter) {

        bannerService.del(parameter.getIdList());
        return REDIRECT_BANNER_LIST_DO;
    }


    private String saveFile(MultipartFile file) {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "./files";
            String saveFilename = FileUtils.getNewSaveFile(baseLocalPath, originalFilename);

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newFile.toPath()));
                return saveFilename.substring(1);
            } catch (IOException e) {
                log.error("############################ - 1");
                log.error("error", e);
            }
        }

        return null;
    }
}
