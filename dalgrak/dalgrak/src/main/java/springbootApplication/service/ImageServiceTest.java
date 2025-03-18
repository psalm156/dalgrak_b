package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import springbootApplication.domain.Cover;
import springbootApplication.dto.CoverUploadDto;
import springbootApplication.repository.CoverRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private CoverRepository coverRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ImageService imageService;

    private CoverUploadDto coverUploadDto;

    @BeforeEach
    void setUp() {
        coverUploadDto = new CoverUploadDto();
        coverUploadDto.setFile(file);
        
        when(file.getOriginalFilename()).thenReturn("test_image.jpg");
        try {
            when(file.getBytes()).thenReturn("image bytes".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCoverUpload() throws Exception {
        
        String expectedFileName = UUID.randomUUID() + "_test_image.jpg";
        Path expectedPath = Paths.get("upload/folder/" + expectedFileName);

        imageService.커버사진업로드(coverUploadDto);

        verify(coverRepository, times(1)).save(any(Cover.class));

        verify(file, times(1)).getBytes();  
        verifyNoMoreInteractions(file);
    }
}
