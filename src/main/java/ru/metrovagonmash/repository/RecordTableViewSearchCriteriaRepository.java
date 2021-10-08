package ru.metrovagonmash.repository;

import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.RecordTableView;
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
public class RecordTableViewSearchCriteriaRepository implements SearchCriteriaView<RecordTableView> {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<RecordTableView> search(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<RecordTableView> query = builder.createQuery(RecordTableView.class);
        final Root r = query.from(RecordTableView.class);

        Predicate predicate = builder.conjunction();
        ProfileViewConsumer searchConsumer = new ProfileViewConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void save(RecordTableView entity) {
        entityManager.persist(entity);
    }

}
