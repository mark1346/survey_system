package survey_system.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.Survey;
import survey_system.demo.repository.SurveyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public void saveSurvey(Survey survey) {
        surveyRepository.save(survey);
    }

    @Transactional
    public void update(Long id, String title, String description) {
        Survey survey = surveyRepository.findOne(id);
        survey.setTitle(title);
        survey.setDescription(description);
    }

    public Survey findOne(Long id) {
        return surveyRepository.findOne(id);
    }

    public List<Survey> findSurveys() {
        return surveyRepository.findAll();
    }

    @Transactional
    public void delete(Survey survey) {
        surveyRepository.delete(survey);
    }

}
