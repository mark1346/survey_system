package survey_system.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.Choice;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.Question;
import survey_system.demo.repository.ChoiceRepository;
import survey_system.demo.repository.OptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionService {
    private final OptionRepository optionRepository;
    private final ChoiceRepository choiceRepository;

    @Transactional
    public void saveOption(Option option){
        optionRepository.save(option);
    }

    @Transactional
    public void updateOption(Long id, String text, int score, Question question){
        Option option = optionRepository.findOne(id);
        option.setId(id);
        option.setOptionText(text);
        option.setScore(score);
        option.setQuestion(question);
    }

    @Transactional
    public void deleteOption(Option option){
        optionRepository.delete(option);
    }

    public Option findOne(Long id){
        return optionRepository.findOne(id);
    }

    public List<Option> findOptions(){
        return optionRepository.findAll();
    }

    @Transactional
    public void deleteOptionWithChoices(Long optionId){
        Option option = optionRepository.findOne(optionId);
        List<Choice> choices = choiceRepository.findByOption(option);
        choiceRepository.deleteAll(choices);
        optionRepository.delete(option);
    }
}
