package survey_system.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a choice made by a user in response to a survey question.
 */
@Entity
@Getter @Setter
public class Choice {
    @Id @GeneratedValue
    @Column(name = "choice_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option option;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Sets the user who made this choice and updates the user's list of choices.
     *
     * @param user The user who made the choice.
     */
    public void setUser(User user) {
        this.user = user;
        user.getChoices().add(this);
    }

    /**
     * Creates a new choice with the specified user and option.
     *
     * @param user   The user who made the choice.
     * @param option The option chosen by the user.
     * @return The newly created Choice object.
     */
    public static Choice createChoice(User user, Option option) {
        Choice choice = new Choice();
        choice.setUser(user);
        choice.setOption(option);

        return choice;
    }
}
