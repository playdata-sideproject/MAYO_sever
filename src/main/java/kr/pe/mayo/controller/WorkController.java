package kr.pe.mayo.controller;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.domain.WorkImg;
import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.domain.dto.WorkImgDTO;
import kr.pe.mayo.service.WorkImgService;
import kr.pe.mayo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WorkController {

    @Autowired
    WorkService workService;

    @Autowired
    WorkImgService workImgService;

    @GetMapping("/upload")
    public String myPage() {
        return "upload";
    }

    // MultipartHttpServletRequest:
    // ServletRequest 를 상속받아 구현된 인터페이스로 파일을 처리하기 위한 여려 메소드 제공
    @PostMapping("/work/uploadWork")
    public String uploadWork(WorkDTO.Upload upload, MultipartHttpServletRequest multipartHttpServletRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
        System.out.println("uploadWork ***************************");
        System.out.println(upload.getCategory());
        workService.uploadWork(upload, multipartHttpServletRequest, principalDetails.getUser());
        return "/";
//        return "upload";


//        return "work/myWorkList";
    }


    @GetMapping("/work/imgs/{workIdx}")
    // 작품별 모든 이미지 조회
    public List<WorkImgDTO> findAllByWork(Long workIdx) {
        return workImgService.findAllByWork(workIdx);
    }

}