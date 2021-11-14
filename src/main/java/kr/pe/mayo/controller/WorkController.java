package kr.pe.mayo.controller;

import kr.pe.mayo.domain.dto.WorkImageDTO;
import kr.pe.mayo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class WorkController {

    @Autowired
    private WorkService workService;

    // MultipartHttpServletRequest : ServletRequest를 상속받아 구현한 인터페이스. 파일 관련 여러 메소드 제공
    @PostMapping("/write-post")
    public String saveWorkImage(WorkImageDTO workImageDTO, MultipartHttpServletRequest multipartHttpServletRequest){
        workService.saveWorkImage(workImageDTO, multipartHttpServletRequest);
        return "redirect:/";
    }
}
