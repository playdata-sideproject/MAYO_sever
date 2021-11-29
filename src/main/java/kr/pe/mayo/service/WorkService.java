package kr.pe.mayo.service;

import kr.pe.mayo.common.FileUtils;
import kr.pe.mayo.dao.WorkImgRepository;
import kr.pe.mayo.dao.WorkRepository;
import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImg;
import kr.pe.mayo.domain.dto.WorkDTO;
import kr.pe.mayo.domain.dto.WorkImgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkService {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WorkImgRepository workImgRepository;

    @Autowired
    private HttpSession session;

    /** 작품 업로드 */
    public void uploadWork(WorkDTO.Upload upload, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {

        // 유저정보와 작품명을 통해 이미 DB에 있는지 확인
        if(workRepository.findByUserIdxAndTitle(upload.getUser(), upload.getTitle()) != null) {
            throw new Exception("동일한 이름으로 등록된 작품이 있습니다");
        } else {
            // 작품 저장 후 저장 된 작품의 유저정보와 작품명으로 idx 찾기
            Work work = workRepository.save(upload.toEntity());

            // 업로드한 이미지를 가공한 리스트 만들기
            List<WorkImgDTO> list = fileUtils.parseFileInfo(work.getIdx(), multipartHttpServletRequest);
            list.forEach(img -> workImgRepository.save(img.toEntity()));
        }

    }

    /** 모든 작품 조회 */
    public List<WorkDTO.List> getAllWork() {
        List<WorkDTO.List> workList = new ArrayList<>();
        List<Work> list = (List<Work>) workRepository.findAll();
        list.forEach(work -> workList.add(new WorkDTO.List(work)));  // 썸네일을 포함한 게시글 전체조회 전용 dto로 변경

        return workList;
    }

    /** 작품 수정 */  // 이게 최선인가................?
    public void updateWork(long workId, WorkDTO.Upload update, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        // 수정한 글에서 받아온 이미지들
        List<WorkImgDTO> newImgs = fileUtils.parseFileInfo(workId, multipartHttpServletRequest);

        // 새로 저장할 이미지들을 담을 빈 리스트
        List<WorkImgDTO> addList = new ArrayList<>();

        // db에 저장돼있는 기존 이미지들 불러온다
        List<WorkImg> oldImgs = workImgRepository.findByWorkIdx(workId);

        if (CollectionUtils.isEmpty(oldImgs)){   // 기존에 저장된 이미지가 하나도 없었다면
            if (!CollectionUtils.isEmpty(newImgs)){
                newImgs.forEach(v -> addList.add(v));  // 수정 게시글의 이미지 모두 추가
            }
        } else{  // 원래 저장돼있는 이미지가 있었다면
            if (CollectionUtils.isEmpty(newImgs)){  // 수정 게시글에 이미지가 없다면
                oldImgs.forEach(v -> workImgRepository.delete(v));  // 기존 이미지들 다 삭제
            } else {
                // 여기서부터가 문제... 수정 게시글에 이미지가 있다면 기존 이미지와 비교해야함
                System.out.println("oldImgs 리스트 내용물: " + oldImgs.get(0));
                for (WorkImg oldImg : oldImgs){
                    String origFileName = oldImg.getOriginalFileName();
                    if (!newImgs.contains(origFileName)){  // 기존 이미지 중에 새로 전달된 이미지 리스트에 없는 것이라면
                        workImgRepository.delete(oldImg);
                    }
                }

                List<String> oldImgOrigNames = new ArrayList<>();
                oldImgs.forEach(v -> oldImgOrigNames.add(v.getOriginalFileName()));
                for (WorkImgDTO newImg : newImgs){
                    if (!oldImgOrigNames.contains(newImg.getOriginalFileName())){  // 새로운 이미지 중에 기존 이미지 리스트에 없는 것이라면
                        addList.add(newImg);
                    }
                }
            }
        }

        // work entity 수정
        Work work = workRepository.findById(workId).get();
        work.setTitle(update.getTitle());
        work.setContent(update.getContent());
        workRepository.save(work);

        // workImg entity 수정 - 최종적으로 새로 저장해야할 이미지들 저장
        addList.forEach(img -> workImgRepository.save(img.toEntity()));
    }
}