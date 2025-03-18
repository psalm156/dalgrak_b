package springbootApplication.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Favorite;
import springbootApplication.domain.Recipe;
import springbootApplication.domain.User;
import springbootApplication.repository.FavoriteRepository;
import springbootApplication.service.FavoriteService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // MockitoExtension을 사용하여 mock 객체를 초기화
public class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    private Favorite favorite;
    private Recipe recipe;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);  // User ID 설정
        user.setUsername("testUser");

        recipe = new Recipe(); // Recipe 객체 생성
        recipe.setRecipeId(1L);  // Recipe ID 설정

        favorite = new Favorite();
        favorite.setUserId(user);  // User 객체 설정
        favorite.setRecipeId(recipe);  // Recipe 객체 설정
    }

    @Test
    void addFavorite_Success() {
        // Mocking
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);

        // 실제 메서드 실행
        Favorite savedFavorite = favoriteService.addFavorite(favorite);

        // 결과 확인
        assertNotNull(savedFavorite);
        assertEquals(favorite.getUserId().getUserId(), savedFavorite.getUserId().getUserId());  // User 객체의 ID 확인
        assertEquals(favorite.getRecipeId().getRecipeId(), savedFavorite.getRecipeId().getRecipeId());  // Recipe 객체의 ID 확인
        verify(favoriteRepository).save(any(Favorite.class));  // save() 메서드 호출 확인
    }

    @Test
    void getFavoriteByUserIdAndRecipeId_Success() {
        // Mocking
        when(favoriteRepository.findByUser_IdAndRecipe_Id(1L, 1L)).thenReturn(Optional.of(favorite));

        // 실제 메서드 실행
        Optional<Favorite> retrievedFavorite = favoriteService.getFavoriteByUser_IdAndRecipe_Id(1L, 1L);

        // 결과 확인
        assertTrue(retrievedFavorite.isPresent());
        assertEquals(favorite.getUserId().getUserId(), retrievedFavorite.get().getUserId().getUserId());  // User 객체의 ID 확인
        assertEquals(favorite.getRecipeId().getRecipeId(), retrievedFavorite.get().getRecipeId().getRecipeId());  // Recipe 객체의 ID 확인
    }

    @Test
    void removeFavorite_Success() {
        // Mocking
        doNothing().when(favoriteRepository).deleteByUser_IdAndRecipe_Id(1L, 1L);

        // 실제 메서드 실행
        favoriteService.removeFavorite(1L, 1L);

        // 결과 확인
        verify(favoriteRepository).deleteByUser_IdAndRecipe_Id(1L, 1L);  // delete() 메서드 호출 확인
    }
}
