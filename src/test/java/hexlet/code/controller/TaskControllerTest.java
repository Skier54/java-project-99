package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.dtoTask.TaskParamsDTO;
import hexlet.code.dto.dtoTask.TaskUpdateDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private Faker faker;

    @Autowired
    private ModelGenerator modelGenerator;

    private Task testTask;

    private User testUser;

    private TaskStatus testTaskStatus;

    private Label testLabel;

    @BeforeEach
    public void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);

        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);

        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();
        labelRepository.save(testLabel);
        Set<Label> labelSet = Set.of(testLabel);

        testTask = Instancio.of(modelGenerator.getTaskModel()).create();

        testTask.setAssignee(testUser);
        testTask.setTaskStatus(testTaskStatus);
        testTask.setLabels(labelSet);
        //taskRepository.save(testTask);
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/tasks")
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    @Test
    public void testIndexWithTitleCont() throws Exception {
        var searchSubstring = testTask.getName().substring(1).toLowerCase();
        var param = new TaskParamsDTO();
        param.setTitleCont(searchSubstring);
        taskRepository.save(testTask);

        var result = mockMvc.perform(get("/api/tasks")
                        .param("titleCont", searchSubstring)
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("title").asString().containsIgnoringCase(searchSubstring))
         );
    }

    @Test
    public void testIndexWithAssigneeId() throws Exception {
        var param = new TaskParamsDTO();
        param.setAssigneeId(testUser.getId());
        taskRepository.save(testTask);

        var result = mockMvc.perform(get("/api/tasks")
                        .param("assigneeId", String.valueOf(testUser.getId()))
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("assignee_id").isEqualTo(testTask.getAssignee().getId()))
        );
    }

    @Test
    public void testIndexWithStatus() throws Exception {
        var param = new TaskParamsDTO();
        param.setStatus(testTaskStatus.getSlug());
        taskRepository.save(testTask);

        var result = mockMvc.perform(get("/api/tasks")
                        .param("status", testTaskStatus.getSlug())
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("status").isEqualTo(testTask.getTaskStatus().getSlug()))
        );
    }

    @Test
    public void testIndexWithLabelId() throws Exception {
        var param = new TaskParamsDTO();
        param.setLabelId(testLabel.getId());
        taskRepository.save(testTask);

        var result = mockMvc.perform(get("/api/tasks")
                        .param("labelId", String.valueOf(testLabel.getId()))
                        .with(jwt()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        var expectedLabelIds = testTask.getLabels().stream()
                .map(Label::getId)
                .toList();

        assertThatJson(body).isArray().allSatisfy(element ->
                assertThatJson(element)
                        .and(v -> v.node("taskLabelIds").isEqualTo(expectedLabelIds))
        );
    }

    @Test
    public void testShow() throws Exception {
        taskRepository.save(testTask);

        var request = get("/api/tasks/" + testTask.getId()).with(jwt());

        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("title").isEqualTo(testTask.getName()),
                v -> v.node("content").isEqualTo(testTask.getDescription()),
                v -> v.node("status").isEqualTo(testTask.getTaskStatus().getSlug()),
                v -> v.node("assignee_id").isEqualTo(testTask.getAssignee().getId())
        );
    }

    @Test
    public void testCreate() throws Exception {
        var data = taskMapper.map(testTask);

        var request = post("/api/tasks")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated());

        var task = taskRepository.findByName(testTask.getName()).orElse(null);

        assertNotNull(task);
        assertThat(task.getName()).isEqualTo(data.getTitle());
        assertThat(task.getDescription()).isEqualTo(data.getContent());
        assertThat(task.getTaskStatus().getSlug()).isEqualTo(data.getStatus());
        assertThat(task.getAssignee().getId()).isEqualTo(data.getAssigneeId());
    }

    @Test
    public void testUpdate() throws Exception {
        taskRepository.save(testTask);

        var newTitle = "New title";
        var newDescription = "New description";
        var dto = new TaskUpdateDTO();
        dto.setTitle(JsonNullable.of(newTitle));
        dto.setContent(JsonNullable.of(newDescription));
        dto.setStatus(JsonNullable.of(testTaskStatus.getSlug()));

        var request = put("/api/tasks/" + testTask.getId())
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var updateTask = taskRepository.findById(testTask.getId()).orElse(null);

        assertThat(updateTask.getName()).isEqualTo(dto.getTitle().get());
        assertThat(updateTask.getDescription()).isEqualTo(dto.getContent().get());
        assertThat(updateTask.getTaskStatus().getSlug()).isEqualTo(dto.getStatus().get());
    }

    @Test
    public void testDestroy() throws Exception {
        taskRepository.save(testTask);
        var request = delete("/api/tasks/" + testTask.getId()).with(jwt());

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(taskRepository.existsById(testTask.getId())).isEqualTo(false);
    }
}
