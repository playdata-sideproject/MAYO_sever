package kr.pe.mayo.controller;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Controller
public class WorkController {

    @Autowired
    private WorkService workService;

    /** 모든 작품 조회 */
    @GetMapping("/work")
    public @ResponseBody List<WorkDTO.List> getAllWork() {
        List<WorkDTO.List> workList = workService.getAllWork();
        return workList;
    }

    @GetMapping("/work/upload")
    public String uploadForm(){
        return "/upload";
    }
    /** 작품 업로드 */
    // MultipartHttpServletRequest:
    // ServletRequest 를 상속받아 구현된 인터페이스로 파일을 처리하기 위한 여려 메소드 제공
    @PostMapping("/work/upload")
    public String uploadWork(WorkDTO.Upload upload, MultipartHttpServletRequest multipartHttpServletRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception{
        System.out.println("넘어온 dto: " + upload.getTitle() + upload.getContent() + upload.getUser());
        upload.setUser(principalDetails.getUser());
        workService.uploadWork(upload, multipartHttpServletRequest);

        return "redirect:/";
    }

    /** 작품 수정 */
    @PutMapping("/work/update/{id}")
    public void updateWork(@PathVariable long workId, WorkDTO.Upload update, MultipartHttpServletRequest multipartHttpServletRequest, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
        update.setUser(principalDetails.getUser());
        workService.updateWork(workId, update, multipartHttpServletRequest);
    }

}