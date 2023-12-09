package survey_system.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.Choice;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.User;
import survey_system.demo.repository.ChoiceRepository;
import survey_system.demo.repository.OptionRepository;
import survey_system.demo.repository.QuestionRepository;
import survey_system.demo.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChoiceService {
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;
    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public Long saveChoice(Long userId, Long optionId){
        // 엔티티조회
        User user = userRepository.findOne(userId);
        Option option = optionRepository.findOne(optionId);

        // 답변 생성
        Choice choice = Choice.createChoice(user, option);

        // 답변 저장
        choiceRepository.save(choice);

        return choice.getId();
    }

    public Choice findOne(Long id){
        return choiceRepository.findOne(id);
    }

    public List<Choice> findChoices(){
        return choiceRepository.findAll();
    }

    @Transactional
    public void deleteChoice(Choice choice){
        choiceRepository.delete(choice);
    }

    @Transactional
    public void updateChoice(Long choiceId, Long userId, Long optionId){
        User user = userRepository.findOne(userId);
        Choice choice = choiceRepository.findOne(choiceId);
        Option option = optionRepository.findOne(optionId);
        choice.setUser(user);
        choice.setOption(option);
    }
}
