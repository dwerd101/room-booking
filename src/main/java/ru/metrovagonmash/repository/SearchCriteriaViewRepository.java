package ru.metrovagonmash.repository;

import ru.metrovagonmash.model.RecordTableView;
import ru.metrovagonmash.specification.SearchCriteria;

import java.util.List;

public interface SearchCriteriaViewRepository<Model> {
    List<Model> search(List<SearchCriteria> params);
    void save(Model entity);
}
