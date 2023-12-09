package survey_system.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import survey_system.demo.domain.Question;
import survey_system.demo.repository.QuestionRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }
    @Transactional
    public void updateQuestion(Long id, String text){
        Question question = questionRepository.findOne(id);
        question.setQuestionText(text);
    }
    @Transactional
    public void deleteQuestion(Question question){
        questionRepository.delete(question);
    }

    public Question findOne(Long id){
        return questionRepository.findOne(id);
    }
    public List<Question> findQuestions(){
        return questionRepository.findAll();
    }

}
