package ch.hearc.smb.repository;

import ch.hearc.smb.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Long> {
    Page<Post> findByBoardIdOrderByModifiedDateDesc(Long boardId, Pageable pageable);
    Page<Post> findTop5ByBoardIdAndNameContainingOrderByModifiedDateDesc(Long boardId, String name, Pageable pageable);
}
