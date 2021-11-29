package kr.pe.mayo.controller;

import kr.pe.mayo.dao.WorkImgRepository;
import kr.pe.mayo.domain.WorkImg;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor // 초기화되지 않은 final 필드에 대해 생성자 생성해줌. 의존성주입의 편의성을 위해 많이 사용됨
@RestController
public class WorkImgController {

    @Autowired
    private final WorkImgRepository workImgRepository;

    // produces = http 응답 헤더로 설정한 타입을 반환한다 (이미지 포맷으로 리턴하기 위한 설정)
    @GetMapping(value = "/thumbnail/{thumbnailId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getThumbnail(@PathVariable long thumbnailId) throws IOException {

        // 이미지가 저장된 절대경로 추출
        String absolutePath = new File("").getAbsolutePath() + File.separator;
        System.out.println("추출된 절대경로: " + absolutePath);

        String path;
        if (thumbnailId != 0){  // 기본이미지가 아니면
            WorkImg img = workImgRepository.findById(thumbnailId).get();
            path = img.getStoredFilePath();
        } else{  // 기본 썸네일이면
            path = "images" + File.separator + "thumbnail" + File.separator + "thumbnail.jpg";
        }
        System.out.println("만든 path: " + path);

        // FileInputStream 객체를 생성하여 이미지가 저장된 경로를 byte[] 형태로 인코딩
        InputStream imgStream = new FileInputStream(absolutePath + path);
        byte[] imgByteArray = IOUtils.toByteArray(imgStream);
        imgStream.close();

        return new ResponseEntity<>(imgByteArray, HttpStatus.OK);
    }
}
