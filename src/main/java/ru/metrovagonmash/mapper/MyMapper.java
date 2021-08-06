package ru.metrovagonmash.mapper;

public interface MyMapper<Model, DTO> {
    DTO toDTO(Model model);
    Model toModel(DTO dto);
}
