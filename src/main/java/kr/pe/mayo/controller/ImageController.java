package kr.pe.mayo.controller;

import kr.pe.mayo.service.WorkImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RequiredArgsConstructor // 초기화되지 않은 final 필드에 대해 생성자 생성해줌. 의존성주입의 편의성을 위해 많이 사용됨
@RestController
public class ImageController {

    private final WorkImgService workImgService;

//    // produces = http 응답 헤더로 설정한 타입을 반환한다 (이미지 포맷으로 리턴하기 위한 설정)
//    @GetMapping(value = "/thumbnail/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
//    public ResponseEntity<byte[]> getThumbnail(@PathVariable long workImgId){
//
//        // 이미지가 저장된 절대경로 추출
//        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
//        System.out.println("absolutePath 값: " + absolutePath);
//
//        String path;
//        if (workImgId != 0){  // 기본이미지가 아니면
//
//        }
//    }
}
