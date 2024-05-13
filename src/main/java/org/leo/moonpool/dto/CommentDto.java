package org.leo.moonpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.leo.moonpool.entity.Comment;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    private String content;

    private Long writerId;

    private Long parentId;

    public Comment toEntity(CommentDto commentDto){
        return Comment.builder()
                .content(commentDto.getContent())
                .writerId(commentDto.getWriterId())
                .parentId(commentDto.getParentId())
                .build();
    }
}
