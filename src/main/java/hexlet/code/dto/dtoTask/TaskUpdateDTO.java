package hexlet.code.dto.dtoTask;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TaskUpdateDTO {

    @NotBlank
    @Size(min = 1)
    private JsonNullable<String> title;

    private JsonNullable<Long> index;

    private JsonNullable<String> content;

    @NotBlank
    private JsonNullable<String> status;

    @JsonProperty("assignee_id")
    private JsonNullable<Long> assigneeId;

    @NotNull
    private JsonNullable<Set<Long>> taskLabelIds;
}
