package hexlet.code.mapper;

import hexlet.code.dto.dtoTaskStatus.TaskStatusCreateDTO;
import hexlet.code.dto.dtoTaskStatus.TaskStatusDTO;
import hexlet.code.dto.dtoTaskStatus.TaskStatusUpdateDTO;
import hexlet.code.model.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskStatusMapper {

    public abstract TaskStatus map(TaskStatusCreateDTO dto);

    public abstract TaskStatusDTO map(TaskStatus taskStatus);

    public abstract void update(TaskStatusUpdateDTO dto, @MappingTarget TaskStatus taskStatus);
}
