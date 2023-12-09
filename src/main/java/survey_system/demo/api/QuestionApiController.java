package survey_system.demo.api;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.Question;
import survey_system.demo.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller handling API requests related to questions in the survey system.
 */
@RestController
@RequiredArgsConstructor
public class QuestionApiController {

    private final QuestionService questionService;

    /**
     * Retrieve a list of all questions.
     *
     * @return List of QuestionDto representing questions.
     */
    @GetMapping("/api/questions")
    public List<QuestionDto> getQuestions(){
        List<Question> allQuestions = questionService.findQuestions();
        List<QuestionDto> collect = allQuestions.stream()
                .map(question -> new QuestionDto(question))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Save a new question.
     *
     * @param request CreateQuestionRequest containing the question text.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/api/questions")
    public ResponseEntity<String> saveQuestion(@RequestBody @Valid CreateQuestionRequest request){
        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        questionService.saveQuestion(question);

        return ResponseEntity.ok("저장되었습니다.");
    }

    /**
     * Update an existing question.
     *
     * @param id      ID of the question to be updated.
     * @param request CreateQuestionRequest containing the updated question text.
     * @return ResponseEntity with a success message.
     */
    @PatchMapping("/api/questions/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable("id") Long id,
                                                 @RequestBody @Valid CreateQuestionRequest request){
        questionService.updateQuestion(id, request.getQuestionText());
        return ResponseEntity.ok("수정되었습니다.");
    }

    /**
     * Delete a question by ID.
     *
     * @param id ID of the question to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/api/questions/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") Long id){
        Question findQuestion = questionService.findOne(id);
        questionService.deleteQuestion(findQuestion);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    /**
     * Request payload for creating a new question.
     */
    @Data
    static class CreateQuestionRequest{
        private String questionText;
    }

    /**
     * Data transfer object (DTO) representing a Question for API responses.
     */
    @Data
    static class QuestionDto {
        private Long id;
        private String text;
        private List<Option> options;

        /**
         * Constructor to create QuestionDto from a Question entity.
         *
         * @param question Question entity to be converted to DTO.
         */
        public QuestionDto(Question question){
            this.id = question.getId();
            this.text = question.getQuestionText();
            this.options = question.getOptions();
        }
    }
}