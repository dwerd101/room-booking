package ru.metrovagonmash.repository.search;

import org.springframework.stereotype.Repository;
import ru.metrovagonmash.config.search.specification.ProfileViewConsumer;
import ru.metrovagonmash.config.search.specification.SearchCriteria;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.repository.SearchCriteriaViewRepository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProfileViewSearchCriteriaRepositoryImpl implements SearchCriteriaViewRepository<ProfileView> {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ProfileView> search(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProfileView> query = builder.createQuery(ProfileView.class);
        final Root r = query.from(ProfileView.class);

        Predicate predicate = builder.conjunction();
        ProfileViewConsumer searchConsumer = new ProfileViewConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void save(ProfileView entity) {
        entityManager.persist(entity);
    }

}
