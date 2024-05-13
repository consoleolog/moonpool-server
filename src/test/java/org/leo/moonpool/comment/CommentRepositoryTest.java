package org.leo.moonpool.comment;

import org.junit.jupiter.api.Test;
import org.leo.moonpool.entity.Comment;
import org.leo.moonpool.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testPost(){
        for (int i = 0; i < 150; i++) {
            Comment comment = Comment.builder()
                    .content("댓글 내용"+i+".....")
                    .parentId(302L)
                    .writerId(1L)
                    .build();
            commentRepository.save(comment);
        }

    }

}
