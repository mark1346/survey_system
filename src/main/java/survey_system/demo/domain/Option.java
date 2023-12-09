package survey_system.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an option for a survey question, with associated choices made by users.
 */
@Entity
@Getter @Setter
public class Option {

    @Id @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private String optionText;
    private int score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @JsonIgnore
    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    private List<Choice> choices = new ArrayList<>();

    /**
     * Sets the question associated with this option and updates the question's list of options.
     *
     * @param question The question to which this option belongs.
     */
    public void setQuestion(Question question) {
        this.question = question;
        question.getOptions().add(this);
    }
}
