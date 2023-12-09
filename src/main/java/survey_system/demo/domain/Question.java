package survey_system.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Represents a survey question, associated with options and part of a survey.
 */
@Entity
@Getter @Setter
public class Question {
    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String questionText;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "survey_id")
    @JsonIgnore
    private Survey survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    /**
     * Sets the survey associated with this question and updates the survey's list of questions.
     *
     * @param survey The survey to which this question belongs.
     */
    public void setSurvey(Survey survey) {
        this.survey = survey;
        survey.getQuestions().add(this);
    }
}
