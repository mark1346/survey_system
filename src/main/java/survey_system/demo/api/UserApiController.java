package survey_system.demo.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import survey_system.demo.domain.User;
import survey_system.demo.service.UserService;

import javax.xml.transform.Result;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller handling API requests related to users in the survey system.
 */
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * Retrieve a list of all users.
     *
     * @return Result containing a list of UserDto representing users.
     */
    @GetMapping("/api/users")
    public Result users() {
        List<User> findUsers = userService.findUsers();
        List<UserDto> collect = findUsers.stream()
                .map(m -> new UserDto(m.getId(), m.getUsername()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    /**
     * Save a new user.
     *
     * @param request CreateUserRequest containing the username and password.
     * @return CreateUserResponse with the ID of the newly created user.
     */
    @PostMapping("/api/users")
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        Long id = userService.save(user);
        return new CreateUserResponse(id);
    }

    /**
     * Update an existing user's username.
     *
     * @param id      ID of the user to be updated.
     * @param request UpdateUserRequest containing the new username.
     * @return UpdateUserResponse with the updated user's ID and username.
     */
    @PatchMapping("/api/users/{id}")
    public UpdateUserResponse updateUser(@PathVariable("id") Long id,
                                           @RequestBody @Valid UpdateUserRequest request) {
        userService.updateName(id, request.getUsername());
        User findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getUsername());
    }

    /**
     * Delete a user by ID.
     *
     * @param id ID of the user to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        User findUser = userService.findOne(id);
        userService.delete(findUser);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    /**
     * Retrieve the total score of a user.
     *
     * @param id ID of the user.
     * @return The total score of the user.
     */
    @GetMapping("/api/users/score/{id}")
    public int totalScore(@PathVariable("id") Long id){
        return userService.totalScore(id);
    }

    /**
     * Request payload for updating a user's username.
     */
    @Data
    static class UpdateUserRequest {
        @NotBlank(message = "변경할 이름 입력해주세요.")
        private String username;
    }

    /**
     * Request payload for creating a new user.
     */
    @Data
    static class CreateUserRequest {
        @NotBlank(message = "이름은 필수입니다.")
        private String username;
        private String password;
    }

    /**
     * Response containing the ID of the newly created user.
     */
    @Data
    static class CreateUserResponse {
        private Long id;

        public CreateUserResponse(Long id) {
            this.id = id;
        }
    }

    /**
     * Response containing the ID and username of an updated user.
     */
    @Data
    @AllArgsConstructor
    static class UpdateUserResponse {
        private Long id;
        private String username;
    }

    /**
     * Result containing a list of data (generically typed).
     *
     * @param <T> Type of the data.
     */
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    /**
     * Data transfer object (DTO) representing a User for API responses.
     */
    @Data
    @AllArgsConstructor
    static class UserDto {
        private Long id;
        private String username;
    }
}
