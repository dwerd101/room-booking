package ru.metrovagonmash.repository;

import ru.metrovagonmash.config.search.specification.SearchCriteria;
import ru.metrovagonmash.model.RecordTableView;


import java.util.List;

public interface SearchCriteriaViewRepository<Model> {
    List<Model> search(List<SearchCriteria> params);
    void save(Model entity);
}
