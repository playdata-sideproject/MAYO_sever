package kr.pe.mayo.controller;

import kr.pe.mayo.domain.dto.WorkImgDTO;
import kr.pe.mayo.service.WorkImgService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkImgController {

    @Autowired
    WorkImgService workImgService;

    // 작품 전체 이미지 조회
    @CrossOrigin
    @GetMapping(
            value = "/thumbnail/{idx}",
            produces = {
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE,
                    MediaType.IMAGE_GIF_VALUE
            }
    )
    public ResponseEntity<byte[]> getWorkThumbnail(@PathVariable Long idx) throws IOException {
        List<WorkImgDTO> workImgDTOList = workImgService.findAllByWork(idx);
        List<ResponseEntity<byte[]>> workImgList = new ArrayList<>();

        for (WorkImgDTO workImgDTO : workImgDTOList) {
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
            String path = workImgDTO.getStoredFilePath();

            InputStream imageStream = new FileInputStream(absolutePath + path);
            byte[] imageByteArray = IOUtils.toByteArray(imageStream);
            imageStream.close();

            workImgList.add(new ResponseEntity<>(imageByteArray, HttpStatus.OK));
        }
        System.out.println(workImgList.get(0));

        return workImgList.get(0);
    }

    // 작품 전체 이미지 조회
    @GetMapping(value = "/images/{idx}"
//            produces = {
//                    MediaType.IMAGE_JPEG_VALUE,
//                    MediaType.IMAGE_PNG_VALUE,
//                    MediaType.IMAGE_GIF_VALUE
//            }
)
    public List<WorkImgDTO> getWorkImg(@PathVariable Long idx) throws IOException {
        List<WorkImgDTO> workImgDTOList = workImgService.findAllByWork(idx);
        return workImgDTOList;
    }

}
