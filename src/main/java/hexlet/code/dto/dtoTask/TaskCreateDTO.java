package hexlet.code.dto.dtoTask;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TaskCreateDTO {

    @NotBlank
    @Size(min = 1)
    private String title;

    private JsonNullable<Long> index;

    private JsonNullable<String> content;

    @NotBlank
    private String status;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;

    private Set<Long> taskLabelIds = new HashSet<>();
}
