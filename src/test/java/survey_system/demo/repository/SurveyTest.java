package survey_system.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.Option;
import survey_system.demo.domain.Question;
import survey_system.demo.domain.Survey;
import survey_system.demo.domain.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SurveyTest {
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void setSurveyRepositoryTest() {
        Survey survey = new Survey();
        survey.setTitle("설문테스트");
        survey.setDescription("설문테스트입니다.");
        surveyRepository.save(survey);

//        User user = new User();
//        user.setUsername("Mark");
//        user.setPassword("1234");
//        userRepository.save(user);
    }



}