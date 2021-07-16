package ru.metrovagonmash.mapper;

import org.dom4j.rule.Mode;

public interface MyMapper<Model, DTO> {
    DTO toDTO(Model model);
    Model toModel(DTO dto);
}
