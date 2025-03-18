package springbootApplication.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import springbootApplication.domain.Cover;
import springbootApplication.dto.CoverUploadDto;
import springbootApplication.repository.CoverRepository;


@RequiredArgsConstructor
@Service
public class ImageService {

    private final CoverRepository coverRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 커버사진업로드(CoverUploadDto coverUploadDto) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + coverUploadDto.getFile().getOriginalFilename();
        System.out.println("커버 이미지 파일이름:"+imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName); // 파일을 저장할 경로 생성

        try {
            Files.write(imageFilePath, coverUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Cover cover = coverUploadDto.toEntity(imageFileName);
        coverRepository.save(cover);
    }
}