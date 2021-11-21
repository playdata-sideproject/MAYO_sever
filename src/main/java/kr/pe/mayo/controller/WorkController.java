package kr.pe.mayo.controller;

import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RestController
public class WorkController {

    @Autowired
    private WorkService workService;

    // MultipartHttpServletRequest:
    // ServletRequest 를 상속받아 구현된 인터페이스로 파일을 처리하기 위한 여려 메소드 제공
    @PostMapping("/work/upload")
    public String uploadWork(WorkDTO.Upload upload, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        workService.uploadWork(upload, multipartHttpServletRequest);
        return "upload";
    }

    /** 모든 작품 조회 */
    @GetMapping("/work/list")
    public List<WorkDTO.Get> getAllWork(){
        List<WorkDTO.Get> workList = workService.getAllWork();
        return workList;
    }
}
