package survey_system.demo.api;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_system.demo.domain.Choice;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.User;
import survey_system.demo.repository.OptionRepository;
import survey_system.demo.repository.UserRepository;
import survey_system.demo.service.ChoiceService;
import survey_system.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller handling API requests related to choices in the survey system.
 */
@RestController
@RequiredArgsConstructor
public class ChoiceApiController {

    private final ChoiceService choiceService;
    private final UserRepository userRepository;
    private final OptionRepository optionRepository;

    /**
     * Retrieve a list of all choices.
     *
     * @return List of ChoiceDto representing choices.
     */
    @GetMapping("/api/choices")
    public List<ChoiceDto> getChoices(){
        List<Choice> allChoices = choiceService.findChoices();
        List<ChoiceDto> collect = allChoices.stream()
                .map(choice -> new ChoiceDto(choice))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * Save a new choice.
     *
     * @param request CreateChoiceRequest containing user name and option ID.
     * @return ResponseEntity with a success message.
     */
    @PostMapping("/api/choices")
    public ResponseEntity<String> saveChoice(@RequestBody @Valid CreateChoiceRequest request){
        Long userId = userRepository.findByName(request.getUserName()).getId();
        Long optionId = request.getOptionId();

        choiceService.saveChoice(userId, optionId);
        return ResponseEntity.ok("저장되었습니다.");
    }

    /**
     * Update an existing choice.
     *
     * @param id      ID of the choice to be updated.
     * @param request CreateChoiceRequest containing user name and option ID.
     * @return ResponseEntity with a success message.
     */
    @PatchMapping("/api/choices/{id}")
    public ResponseEntity<String> updateChoice(@PathVariable("id") Long id,
                                               @RequestBody @Valid CreateChoiceRequest request){
        Long userId = userRepository.findByName(request.getUserName()).getId();
        Long optionId = request.getOptionId();

        choiceService.updateChoice(id, userId, optionId);
        return ResponseEntity.ok("수정되었습니다.");
    }

    /**
     * Delete a choice by ID.
     *
     * @param id ID of the choice to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/api/choices/{id}")
    public ResponseEntity<String> deleteChoice(@PathVariable("id") Long id){
        Choice findChoice = choiceService.findOne(id);
        choiceService.deleteChoice(findChoice);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    /**
     * Request payload for creating a new choice.
     */
    @Data
    static class CreateChoiceRequest{
        private String userName;
        private Long optionId;
    }

    /**
     * Data transfer object (DTO) representing a Choice for API responses.
     */
    @Data
    static class ChoiceDto {
        private Long choiceId;
        private String userName;
        private Long optionId;

        /**
         * Constructor to create ChoiceDto from a Choice entity.
         *
         * @param choice Choice entity to be converted to DTO.
         */
        public ChoiceDto(Choice choice) {
            this.choiceId = choice.getId();
            this.userName = choice.getUser().getUsername();
            this.optionId = choice.getOption().getId();
        }
    }
}