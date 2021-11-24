package kr.pe.mayo.service;

import kr.pe.mayo.dao.WorkImgRepository;
import kr.pe.mayo.domain.WorkImg;
import kr.pe.mayo.domain.dto.WorkImgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WorkImgService {
    
    @Autowired
    WorkImgRepository workImgRepository;


    // 0번 인덱스로 썸내일 설정
    public WorkImgDTO getThumbnail(Long workIdx) {
        return new WorkImgDTO(workImgRepository.findAllByWorkIdx(workIdx).get(0));
    }

    public List<WorkImgDTO> findAllByWork(Long workIdx) {

        List<WorkImg> workImgList = workImgRepository.findAllByWorkIdx(workIdx);


        return workImgList.stream()
                .map(WorkImgDTO::new)
                .collect(Collectors.toList());

//        List<WorkImgDTO> workImgDTOList = new ArrayList<>();
//
//        workImgList.forEach(WorkImg -> {
//            WorkImgDTO workImgDTO = new WorkImgDTO(WorkImg);
//            workImgDTOList.add(workImgDTO);
//        });

//        return workImgDTOList;
    }
    
}
