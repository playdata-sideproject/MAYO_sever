package kr.pe.mayo.controller;

import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@Controller
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("/write-post")
    public String postForm(){
        return "/postForm";
    }

    // MultipartHttpServletRequest : ServletRequest를 상속받아 구현한 인터페이스. 파일 관련 여러 메소드 제공
    @PostMapping("/insertPost")
    public String insertPost(WorkDTO workDTO, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        System.out.println(workDTO);  // WorkDTO(catIdx=1, workTitle=제목, workContent=내용, workImages=[MultipartFile[field="workImages", filename=algorithm.png, contentType=image/png, size=34789]])

        workService.insertPost(workDTO, multipartHttpServletRequest);
        return "redirect:/";
    }
}
