package ru.metrovagonmash.repository.search;

import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.repository.SearchCriteriaViewRepository;
import ru.metrovagonmash.specification.ProfileViewConsumer;
import ru.metrovagonmash.specification.SearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DepartmentSearchCriteriaRepositoryImpl implements SearchCriteriaViewRepository<Department> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Department> search(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Department> query = builder.createQuery(Department.class);
        final Root r = query.from(Department.class);

        Predicate predicate = builder.conjunction();
        ProfileViewConsumer searchConsumer = new ProfileViewConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void save(Department entity) {
        entityManager.persist(entity);
    }
}
