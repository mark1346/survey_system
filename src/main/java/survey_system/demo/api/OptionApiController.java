package survey_system.demo.api;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.Question;
import survey_system.demo.repository.QuestionRepository;
import survey_system.demo.service.OptionService;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
public class OptionApiController {

    private final OptionService optionService;
    private final QuestionRepository questionRepository;

    /**
     * Retrieve a list of all options.
     *
     * @return List of OptionDto representing options.
     */
    @GetMapping("/api/options")
    public List<OptionDto> getOptions(){
        List<Option> allOptions = optionService.findOptions();
        List<OptionDto> collect = allOptions.stream()
                .map(option -> new OptionDto(option))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Save a new option.
     *
     * @param request CreateOptionRequest containing option text, score, and question ID.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/api/options")
    public ResponseEntity<String> saveOption(@RequestBody @Valid CreateOptionRequest request){
        Option option = new Option();
        option.setOptionText(request.getOptionText());
        option.setScore(request.getScore());

        Question question = questionRepository.findOne(request.getQuestionId());
        option.setQuestion(question);
        optionService.saveOption(option);

        return ResponseEntity.ok("저장되었습니다.");
    }

    /**
     * Update an existing option.
     *
     * @param id      ID of the option to be updated.
     * @param request CreateOptionRequest containing option text, score, and question ID.
     * @return ResponseEntity with a success message.
     */
    @PatchMapping("/api/options/{id}")
    public ResponseEntity<String> updateOption(@PathVariable("id") Long id,
                                               @RequestBody @Valid CreateOptionRequest request){
        Question question = questionRepository.findOne(request.getQuestionId());
        optionService.updateOption(id, request.getOptionText(), request.getScore(), question);
        return ResponseEntity.ok("수정되었습니다.");
    }

    /**
     * Delete an option by ID.
     *
     * @param id ID of the option to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/api/options/{id}")
    public ResponseEntity<String> deleteOption(@PathVariable("id") Long id){
        Option findOption = optionService.findOne(id);
        optionService.deleteOption(findOption);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    /**
     * Request payload for creating a new option.
     */
    @Data
    static class CreateOptionRequest{
        private String optionText;
        private int score;
        private Long questionId;
    }

    /**
     * Data transfer object (DTO) representing an Option for API responses.
     */
    @Data
    static class OptionDto{
        private Long id;
        private String optionText;
        private int score;
        private Long questionId;

        /**
         * Constructor to create OptionDto from an Option entity.
         *
         * @param option Option entity to be converted to DTO.
         */
        public OptionDto(Option option){
            this.id = option.getId();
            this.optionText = option.getOptionText();
            this.score = option.getScore();
            this.questionId = option.getQuestion().getId();
        }
    }
}

