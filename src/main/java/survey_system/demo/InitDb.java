package survey_system.demo;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.*;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit1() {
            User user1 = createUser("Mark", "1234");
            em.persist(user1);

            Survey survey1 = createSurvey("설문1", "음식취향설문");
            em.persist(survey1);

            Question question1 = createQuestion("좋아하는 한식은?", survey1);
            em.persist(question1);
            Question question2 = createQuestion("좋아하는 중식은?", survey1);
            em.persist(question2);

            Option option1_1 = createOption("김치찌개", question1, 7);
            em.persist(option1_1);
            Option option1_2 = createOption("된장찌개", question1, 8);
            em.persist(option1_2);

            Option option2_1 = createOption("짜장면", question2, 6);
            em.persist(option2_1);
            Option option2_2 = createOption("짬뽕", question2, 9);
            em.persist(option2_2);

            Choice choice_q1 = createChoice(option1_2, user1);
            em.persist(choice_q1);

            Choice choice_q2 = createChoice(option2_2, user1);
            em.persist(choice_q2);


        }
    }

    private static Choice createChoice(Option option, User user) {
        Choice choice = new Choice();
        choice.setOption(option);
        choice.setUser(user);
        return choice;
    }

    private static Option createOption(String optionText, Question question, int score) {
        Option option = new Option();
        option.setOptionText(optionText);
        option.setQuestion(question);
        option.setScore(score);
        return option;
    }

    private static Question createQuestion(String questionText, Survey survey) {
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setSurvey(survey);
        return question;
    }

    private static Survey createSurvey(String title, String description) {
        Survey survey = new Survey();
        survey.setTitle(title);
        survey.setDescription(description);
        return survey;
    }

    private static User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}

