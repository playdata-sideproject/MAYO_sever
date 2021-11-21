package kr.pe.mayo.controller;

import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("/upload")
    public String myPage() {
        return "upload";
    }

    // MultipartHttpServletRequest:
    // ServletRequest 를 상속받아 구현된 인터페이스로 파일을 처리하기 위한 여려 메소드 제공
    @PostMapping("/work/uploadWork")
    public String uploadWork(WorkDTO.Upload upload, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        workService.uploadWork(upload, multipartHttpServletRequest);
        return "upload";
    }
}
