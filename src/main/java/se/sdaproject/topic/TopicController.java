package se.sdaproject.topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.article.Article;
import se.sdaproject.ResourceNotFoundException;
import se.sdaproject.article.ArticleRepository;

import java.util.List;
import java.util.Set;

@RestController
public class TopicController {
    TopicRepository topicRepository;
    ArticleRepository articleRepository;

    @Autowired
    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository){
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }


    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topicParam){
        return ResponseEntity.status(HttpStatus.CREATED).body(topicRepository.save(topicParam));
    }


    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics(){
        return ResponseEntity.ok(topicRepository.findAll());
    }


    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<Set<Article>> getAllArticlesAssociatedWithTopic(@PathVariable Long topicId){
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(topic.getArticlesList());
    }


    @PutMapping("/topics/{topicId}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long topicId, @RequestBody Topic topicParam){
        Topic existingTopic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        topicParam.setId(existingTopic.getId());
        topicRepository.save(topicParam);
        return ResponseEntity.ok(topicParam);
    }


    @DeleteMapping("/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        topicRepository.delete(topic);
    }


    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topic> associatesTopicWithArticle(@PathVariable Long articleId, @RequestBody Topic topicParam){
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        boolean doesExist = true;
        if(topicRepository.findById(topicParam.getId()).isEmpty()){
            topicRepository.save(topicParam);
            doesExist=false;
        }
        topicParam.getArticlesList().add(article);
        topicRepository.save(topicParam);
        if(!doesExist){
            return ResponseEntity.status(HttpStatus.CREATED).body(topicParam);
        }else{
            return ResponseEntity.ok(topicParam);
        }
    }


    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long articleId, @PathVariable Long topicId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        if (topic.getArticlesList().contains(article)) {
            topic.getArticlesList().remove(article);
            topicRepository.save(topic);
        } else{
            throw new ResourceNotFoundException();
        }
    }




}