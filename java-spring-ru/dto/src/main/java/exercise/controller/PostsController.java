package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    // Пользователь автоматически преобразуется в JSON
    public List<PostDTO> showAll() {
        List<PostDTO> dto = new ArrayList<>();
        var posts = postRepository.findAll();

        for (Post p : posts) {
            var comments = commentRepository.findByPostId(p.getId());
            var commentsDTO = comments.stream().map(this::toCommentDTO).toList();
            dto.add(toPostDTO(p,commentsDTO));
        }

        return dto;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    // Пользователь автоматически преобразуется в JSON
    public PostDTO show(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        var comments = commentRepository.findByPostId(id);


        var commentsDTO = comments.stream().map(this::toCommentDTO).toList();

        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(commentsDTO);

        return dto;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }

    private PostDTO toPostDTO(Post post, List<CommentDTO> commentDTOList) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(commentDTOList);

        return dto;
    }

}
// END
