package se.sdaproject.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.article.Article;
import se.sdaproject.article.ArticleRepository;
import se.sdaproject.ResourceNotFoundException;

import java.util.List;

@RestController
public class CommentController {
    CommentRepository commentRepository;
    ArticleRepository articleRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }


    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long articleId, @Validated @RequestBody Comment comment){
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        comment.setCommentedArticle(article);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);

    }


    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<Comment>> getArticlesCommentsList(@PathVariable Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article.getArticleCommentsList());
    }


    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long id){
        commentRepository.deleteById(id);
    }


    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @Validated @RequestBody Comment commentParams){
        Comment existingComment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentParams.setId(id);
        commentRepository.save(commentParams);
        return ResponseEntity.ok(commentParams);
    }


    @GetMapping(value = "/comments", params = {"authorName"})
    public ResponseEntity<List<Comment>> viewAllCommentsMadeByAuthor(@RequestParam String authorName) {
        return ResponseEntity.ok(commentRepository.findByAuthorName(authorName));
    }


}