package kr.pe.mayo.service;

import kr.pe.mayo.common.FileUtils;
import kr.pe.mayo.dao.CategoryRepository;
import kr.pe.mayo.dao.WorkImageRepository;
import kr.pe.mayo.dao.WorkRepository;
import kr.pe.mayo.domain.Category;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImage;
import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.domain.dto.WorkImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private WorkImageRepository workImageRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private HttpSession session;

    public void insertPost(WorkDTO workDTO, MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        // Work 테이블에 게시글부터 저장
        Work work = new Work();

        User user = (User) session.getAttribute("user");
        Category category = categoryRepository.findById(workDTO.getCatIdx()).get();

        System.out.println(user);
        System.out.println(category);

        work.setUserIdx(user);
        work.setCatIdx(category);
        work.setWorkTitle(workDTO.getWorkTitle());
        work.setWorkContent(workDTO.getWorkContent());
        work.setWorkStatus('0');

        //  insert into work (cat_idx_cat_idx, user_idx_user_idx, work_content, work_created_at, work_status, work_title) values (?, ?, ?, ?, ?, ?)  ?????????
        Work newWork = workRepository.save(work); // 저장되면서 자동부여된 work 테이블의 인덱스 가져오기

        List<WorkImageDTO> dtoList = fileUtils.parseFileInfo(multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(dtoList) == false) {
            List<WorkImage> list = new ArrayList<>();
            dtoList.forEach(v -> list.add(new WorkImage().builder()
                    .workIdx(newWork)
                    .origFileName(v.getOrigFileName())
                    .filePath(v.getFilePath())
                    .fileSize(v.getFileSize())
                    .build()
            ));
            workImageRepository.saveAll(list);
        }
    }
}
