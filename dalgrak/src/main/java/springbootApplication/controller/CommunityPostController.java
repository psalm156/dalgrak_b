package springbootApplication.controller;

import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.CommunityPostType;
import springbootApplication.service.CommunityPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
@Tag(name = "Community", description = "Manage community posts")
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    public CommunityPostController(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }

    @GetMapping
    @Operation(summary = "Get all community posts", description = "Retrieve all community posts")
    public List<CommunityPost> getAllPosts() {
        return communityPostService.getAllPosts();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<CommunityPost>> getPostsByType(@RequestParam CommunityPostType type) {
        List<CommunityPost> posts = communityPostService.getPostsByType(type);
        return ResponseEntity.ok(posts);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search posts by title", description = "Retrieve community posts by title")
    public ResponseEntity<List<CommunityPost>> getPostsByTitle(@RequestParam String title) {
        return communityPostService.getPostsByTitle(title)
                .map(posts -> ResponseEntity.ok(posts))
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }


    @PostMapping
    @Operation(summary = "Create a new community post", description = "Add a new post to the community board")
    public ResponseEntity<CommunityPost> createPost(@RequestBody CommunityPost post) {
        try {
            CommunityPost createdPost = communityPostService.createPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost); // 201 CREATED 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // 잘못된 요청 처리
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a community post", description = "Update an existing community post")
    public ResponseEntity<CommunityPost> updatePost(
            @PathVariable Long id, 
            @RequestBody CommunityPost updatedPost, 
            @RequestParam Long userId) { 
        
        try {
            CommunityPost updated = communityPostService.updatePost(id, updatedPost, userId);
            return ResponseEntity.ok(updated); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a community post", description = "Delete a community post by ID")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam Long userId) {
        try {
            communityPostService.deletePost(id, userId);
            return ResponseEntity.noContent().build(); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); 
        }
    }
}
