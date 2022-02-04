package me.ktkim.blog.api;

import lombok.extern.slf4j.Slf4j;
import me.ktkim.blog.model.domain.Comment;
import me.ktkim.blog.model.dto.CommentDto;
import me.ktkim.blog.security.CurrentUser;
import me.ktkim.blog.security.service.CustomUserDetails;
import me.ktkim.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kim Keumtae
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/comments/posts/{postId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId) {
        log.debug("REST request to getComments : {}", postId);

        Optional<List<Comment>> comments = commentService.findCommentsByPostId(postId);
        if (!comments.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<List<CommentDto>>(comments
                    .get()
                    .stream()
                    .map(CommentDto::new)
                    .collect(Collectors.toList()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/comments/posts/{postId}")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto,
                                                  @CurrentUser CustomUserDetails customUserDetails) {
        log.debug("REST request to saveComment : {}", commentDto.getUserName());
        CommentDto returnComment = commentService.registerComment(commentDto, customUserDetails);
        return new ResponseEntity<CommentDto>(returnComment, HttpStatus.CREATED);
    }
}
