package kr.pe.mayo.common;

import kr.pe.mayo.domain.dto.WorkImgDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
    첨부파일 정보를 가공하고 지정된 위치에 파일을 저장하는 기능 필요
 */

@Component
public class FileUtils {

    public List<WorkImgDTO> parseFileInfo(Long workIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<WorkImgDTO> imgList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/" + current.format(format);
        File file = new File(path);
        if(!file.exists()) {
            file.mkdir();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        // 서버에 저장될 때 사용될 이름
        String newFileName;
        String originalFileExtension;
        String contentType = null;

        while(iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for(MultipartFile multipartFile : list) {
                if(!multipartFile.isEmpty()) {
                    contentType = multipartFile.getContentType();
                    if(ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else {
                        if(contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        } else if(contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        } else if(contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else {
                            break;
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                    WorkImgDTO workImgFile = new WorkImgDTO();
                    workImgFile.setWorkIdx(workIdx);
                    workImgFile.setFileSize(multipartFile.getSize());
                    workImgFile.setOriginalFileName(multipartFile.getOriginalFilename());
                    workImgFile.setStoredFilePath(path + "/" + newFileName);
                    imgList.add(workImgFile);

                    file = new File(path + "/" + newFileName);
                    multipartFile.transferTo(file);

                }
            }
        }

        return imgList;
    }
}
