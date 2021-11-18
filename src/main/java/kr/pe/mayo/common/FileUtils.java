package kr.pe.mayo.common;

import kr.pe.mayo.domain.dto.WorkImageDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/** FileUtils 클래스 :  ~ 하는 역할 */
@Component
public class FileUtils {

    public List<WorkImageDTO> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {

        // 예외처리 - request에서 파일 안넘어온 경우
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)){
            return null;
        }

        List<WorkImageDTO> fileList = new ArrayList<>();

        // 이미지파일을 저장할 디렉토리 만듬 - images/오늘날짜
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/" + current.format(format);
        File dir = new File(path);
        if (dir.exists() == false){   // 만들어진 디렉토리가 없는 경우 새로 만든다
            dir.mkdirs();
        }

        // getFileNames(): 서버로 한번에 전송되는 한 개 이상의 파일 태그 이름(= html input 태그의 name속성)을 iterator 형식으로 가져올 수 있음
        // 한 페이지에 파일 태그가 여러개 있을수도 있으므로 iterator 형식으로 가져옴
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();


        String originalFileExtension;  // 이미지 확장자를 담을 변수
        String contentType;  // getContentType 함수로 얻은 값을 담을 변수
        String newFileName;  // 위 두 변수를 사용해 만들 절대 중복되지 않을 새로운 파일이름

        while (iterator.hasNext()){
            // input 태그를 통해 입력받은 파일들 (multiple 속성을 통해 한 태그로 여러개 파일 받을 수 있으므로 List로 받는다)
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list){
                if (multipartFile.isEmpty() == false){
                    // 파일 이름에서 확장자 가져오지 말고 getContentType 함수를 이용하는 것이 안전 (nio 패키지, Apache Tika 라이브러리 이용하라는데 그건 뭔말인지 모르겠음)
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else{
                        if (contentType.contains("image/jpeg")){
                            originalFileExtension = ".jpg";
                        } else if (contentType.contains("image/png")){
                            originalFileExtension = ".png";
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else {
                            break;
                        }
                    }

                    // 절대 중복되지 않을 새로운 파일 이름 생성 - 파일이 저장되는 시간 나노초 + 확장자
                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

                    WorkImageDTO workImage = new WorkImageDTO();
//                    workImage.setWorkIdx(workIdx);
                    workImage.setOrigFileName(multipartFile.getOriginalFilename());
                    workImage.setFilePath(path + "/" + newFileName);
                    workImage.setFileSize(multipartFile.getSize());

                    fileList.add(workImage);

                    dir = new File(path + "/" + newFileName);
                    multipartFile.transferTo(dir);  // transferTo: 경로에 파일 저장하는 함수
                }
            }
        }
        return fileList;
    }
}
