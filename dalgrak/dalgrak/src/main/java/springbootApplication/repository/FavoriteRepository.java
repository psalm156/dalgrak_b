package springbootApplication.repository;

import springbootApplication.domain.Favorite;
import springbootApplication.domain.Favorite.FavoriteId; // FavoriteId는 내부 클래스로 접근
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    // user와 recipe 객체의 ID를 기준으로 Favorite을 조회하는 메소드
    Optional<Favorite> findByUser_IdAndRecipe_Id(Long userId, Long recipeId);

    // user와 recipe 객체의 ID를 기준으로 Favorite을 삭제하는 메소드
    void deleteByUser_IdAndRecipe_Id(Long userId, Long recipeId);
}
