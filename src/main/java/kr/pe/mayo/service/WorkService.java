package kr.pe.mayo.service;

import kr.pe.mayo.dao.WorkImageRepository;
import kr.pe.mayo.domain.dto.WorkImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@Service
public class WorkService {

    @Autowired
    private WorkImageRepository workImageRepository;

    public long saveWorkImage(WorkImageDTO workImageDTO, MultipartHttpServletRequest multipartHttpServletRequest){
        // ObjectUtils :
        if (ObjectUtils.isEmpty(multipartHttpServletRequest) == false){
            // 하나의 input 태그로 여러 파일
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

            String name;
            while (iterator.hasNext()){
                name = iterator.next();
                List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
                for (MultipartFile multipartFile : list) {

                }

            }
        }
        return workImageRepository.save(workImageDTO.toEntity()).getImgIdx();
    }
}
