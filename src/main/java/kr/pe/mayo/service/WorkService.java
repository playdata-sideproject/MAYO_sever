package kr.pe.mayo.service;

import kr.pe.mayo.domain.dto.WorkDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class WorkService {
    public void uploadWork(WorkDTO.Upload work, MultipartHttpServletRequest multipartHttpServletRequest){
        System.out.println("did it run?");
        if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            String name;
            while(iterator.hasNext()) {
                name = iterator.next();
                System.out.println("file tag name : " + name);
//                log.debug("file tag name : " + name);
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);

                // 업로드 한 파일 정보 출력
                // 사이트처럼 로깅 설정이 안되어있기 때문에 일단 시스템 출력으로...!
                list.forEach(v -> {
                    System.out.println("start file information");
                    System.out.println("file name : " + v.getOriginalFilename());
                    System.out.println("file size : " + v.getSize());
                    System.out.println("file content type : " + v.getContentType());
                    System.out.println("end file information \n");
//                    log.debug("start file information");
//                    log.debug("file name : " + v.getOriginalFilename());
//                    log.debug("file size : " + v.getSize());
//                    log.debug("file content type : " + v.getContentType());
//                    log.debug("end file information \n");
                });
            }
        }
    }
}
