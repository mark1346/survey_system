package survey_system.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the survey system with associated choices and a total score calculation logic.
 */
@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Choice> choices = new ArrayList<>();

    /**
     * Adds a choice to the user's list of choices and sets the user for the choice (bidirectional relationship).
     *
     * @param choice The choice to be added.
     */
    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setUser(this); // Bidirectional relationship setup
    }

    /**
     * Calculates and returns the total score of the user based on their choices.
     *
     * @return The total score of the user.
     */
    public int getTotalScore() {
        int totalScore = 0;
        for (Choice choice : choices) {
            totalScore += choice.getOption().getScore();
        }
        return totalScore;
    }
}
