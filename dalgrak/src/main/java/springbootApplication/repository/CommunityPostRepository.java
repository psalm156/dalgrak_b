package springbootApplication.repository;

import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.CommunityPostType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

	  List<CommunityPost> findByPostType(CommunityPostType postType);
	  List<CommunityPost> findByTitleContaining(String title);
}