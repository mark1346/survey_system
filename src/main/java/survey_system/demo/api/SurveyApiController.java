package survey_system.demo.api;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.Question;
import survey_system.demo.domain.Survey;
import survey_system.demo.service.SurveyService;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
public class SurveyApiController {

    private final SurveyService surveyService;

    /**
     * Retrieve a list of all surveys.
     *
     * @return List of SurveyDto representing surveys.
     */
    @GetMapping("/api/surveys")
    public List<SurveyDto> getSurveys() {
        List<Survey> allSurveys = surveyService.findSurveys();
        List<SurveyDto> collect = allSurveys.stream()
                .map(survey -> new SurveyDto(survey))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Save a new survey.
     *
     * @param request CreateSurveyRequest containing the survey title and description.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/api/surveys")
    public ResponseEntity<String> saveSurvey(@RequestBody @Valid CreateSurveyRequest request){
        Survey survey = new Survey();
        survey.setTitle(request.getTitle());
        survey.setDescription(request.getDescription());
        surveyService.saveSurvey(survey);

        return ResponseEntity.ok("저장되었습니다.");
    }

    /**
     * Update an existing survey.
     *
     * @param id      ID of the survey to be updated.
     * @param request CreateSurveyRequest containing the updated title and description.
     * @return ResponseEntity with a success message.
     */
    @PatchMapping("/api/surveys/{id}")
    public ResponseEntity<String> updateSurvey(@PathVariable("id") Long id,
                                               @RequestBody @Valid CreateSurveyRequest request){
        surveyService.update(id, request.getTitle(), request.getDescription());
        return ResponseEntity.ok("수정되었습니다.");
    }

    /**
     * Delete a survey by ID.
     *
     * @param id ID of the survey to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/api/surveys/{id}")
    public ResponseEntity<String> deleteSurvey(@PathVariable("id") Long id){
        Survey findSurvey = surveyService.findOne(id);
        surveyService.delete(findSurvey);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    /**
     * Request payload for creating a new survey.
     */
    @Data
    static class CreateSurveyRequest{
        private String title;
        private String description;
    }

    /**
     * Data transfer object (DTO) representing a Survey for API responses.
     */
    @Data
    static class SurveyDto{
        private Long id;
        private String title;
        private String description;
        private List<QuestionDto> questions;

        /**
         * Constructor to create SurveyDto from a Survey entity.
         *
         * @param survey Survey entity to be converted to DTO.
         */
        public SurveyDto(Survey survey){
            this.id = survey.getId();
            this.title = survey.getTitle();
            this.description = survey.getDescription();
            this.questions = survey.getQuestions().stream()
                    .map(question -> new QuestionDto(question))
                    .collect(Collectors.toList());
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
}
