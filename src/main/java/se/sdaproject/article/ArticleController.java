package se.sdaproject.article;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.ResourceNotFoundException;
import se.sdaproject.topic.Topic;
import se.sdaproject.topic.TopicRepository;

import java.util.List;
import java.util.Set;

@RestController
public class ArticleController {
    ArticleRepository articleRepository;
    TopicRepository topicRepository;


    @Autowired
    public ArticleController(ArticleRepository articleRepository, TopicRepository topicRepository){
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
    }


    @GetMapping("/articles/{id}")
    public Article getArticle(@PathVariable Long id){
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return article;
    }


    @PostMapping("/articles")
    public Article postArticle(@RequestBody Article article){
        articleRepository.save(article);
        return article;
    }


    @GetMapping("/articles")
    public List<Article> getArticlesList(){
        return articleRepository.findAll();
    }


    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id){
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        articleRepository.deleteById(id);
    }


    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle){
        Article existingArticle = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle.setId(id);
        articleRepository.save(updatedArticle);
        return ResponseEntity.ok(updatedArticle);
    }

    @GetMapping("/articles/{articleId}/topics")
    public ResponseEntity<Set<Topic>> getAllTopics(@PathVariable Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article.getTopicsList());
    }



}