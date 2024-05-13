package org.leo.moonpool.repository;

import org.leo.moonpool.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c ,m FROM Comment c INNER JOIN Member m ON c.writerId=m.memberId WHERE c.parentId=:problemId ORDER BY c.commentId DESC LIMIT 10 OFFSET :offset")
    List<?> customFindAll(@Param("problemId")Long problemId,@Param("offset") int offset);

    @Query(value = "SELECT COUNT (c) FROM Comment c WHERE c.parentId=:problemId")
    Long countByProblemId(@Param("problemId")Long problemId);
}
