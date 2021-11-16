package kr.pe.mayo.service;

import kr.pe.mayo.common.FileUtils;
import kr.pe.mayo.dao.WorkImgFileRepository;
import kr.pe.mayo.dao.WorkRepository;
import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImgFile;
import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.domain.dto.WorkImgFileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;

@Service
public class WorkService {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkImgFileRepository workImgFileRepository;

    public void uploadWork(WorkDTO.Upload upload, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {


        // 유저정보와 작품명을 통해 이미 DB에 있는지 확인
        if(workRepository.findByUserIdxAndWorkTitle(upload.getUser(), upload.getWorkTitle()) != null) {
            throw new Exception("동일한 이름으로 등록된 작품이 있습니다");
        } else {
            // 작품 저장 후 저장 된 작품의 유저정보와 작품명으로 idx 찾기
            workRepository.save(upload.toEntity());
            Work work = workRepository.findByUserIdxAndWorkTitle(upload.getUser(), upload.getWorkTitle());
            System.out.println("uploaded work : " + work);
            System.out.println("********* work id : " + work.getWorkIdx());

            // 업로드한 이미지를 가공한 리스트 만들기
            List<WorkImgFileDTO> list = fileUtils.parseFileInfo(work.getWorkIdx(), multipartHttpServletRequest);
            list.forEach(img -> workImgFileRepository.save(img.toEntity()));
            System.out.println(list);
            /*
            [
                WorkImgFileDTO(
                    workIdx=1,
                    originalFileName=image.png,
                    storedFilePath=images/20211116/290492099140900.png,
                    fileSize=46877)
            ]
             */
        }




//        System.out.println(work.toString());

        // 파일정보 확인용
        if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            String name;
            while(iterator.hasNext()) {
                name = iterator.next();
                System.out.println("file tag name : " + name);
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);

                list.forEach(v -> {
                    System.out.println("start file information");
                    System.out.println("file name : " + v.getOriginalFilename());
                    System.out.println("file size : " + v.getSize());
                    System.out.println("file content type : " + v.getContentType());
                    System.out.println("end file information \n");
                });
            }
        }
    }
}
