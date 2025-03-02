package springbootApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import springbootApplication.dto.CoverUploadDto;
import springbootApplication.service.ImageService;


@Controller
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    
@PostMapping("/")
public String coverImageUpload(CoverUploadDto coverUploadDto) {
    
    if(coverUploadDto.getFile().isEmpty()) {
        throw new RuntimeException("이미지가 첨부되지 않았습니다.", null);
    }

    imageService.커버사진업로드(coverUploadDto);
    return "redirect:/";
	}
}