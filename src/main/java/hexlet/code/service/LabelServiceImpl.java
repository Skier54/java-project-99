package hexlet.code.service;

import hexlet.code.dto.dtoLabel.LabelCreateDTO;
import hexlet.code.dto.dtoLabel.LabelDTO;
import hexlet.code.dto.dtoLabel.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    @Override
    public List<LabelDTO> getAllLabels() {
        var labels = labelRepository.findAll();
        return labels.stream()
                .map(labelMapper::map)
                .toList();
    }

    @Override
    public LabelDTO createLabel(LabelCreateDTO labelData) {
        var label = labelMapper.map(labelData);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @Override
    public LabelDTO getLabelById(Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Метка с идентификатором " + id + " не найдена"));
        return labelMapper.map(label);
    }

    @Override
    public LabelDTO updateLabel(LabelUpdateDTO labelData, Long id) {
        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Метка с идентификатором " + id + " не найдена"));
        labelMapper.update(labelData, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    @Override
    public void deleteLabel(Long id) {

        var label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Метка с идентификатором " + id + " не найдена"));

//        if (!label.getTasks().isEmpty()) {
//            throw new IllegalStateException(
//                    "Метка не может быть удалёна: она связана с задачей"
//            );
//        }
        labelRepository.deleteById(id);
    }
}
