package hexlet.code.service;

import hexlet.code.dto.dtoLabel.LabelCreateDTO;
import hexlet.code.dto.dtoLabel.LabelDTO;
import hexlet.code.dto.dtoLabel.LabelUpdateDTO;

import java.util.List;


public interface LabelService {
    List<LabelDTO> getAllLabels();
    LabelDTO createLabel(LabelCreateDTO labelData);
    LabelDTO getLabelById(Long id);
    LabelDTO updateLabel(LabelUpdateDTO labelData, Long id);
    void deleteLabel(Long id);
}
