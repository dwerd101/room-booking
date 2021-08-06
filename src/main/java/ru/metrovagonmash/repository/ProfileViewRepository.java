package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.model.RecordTableView;

import java.util.List;

@Repository
public interface ProfileViewRepository extends JpaRepository<ProfileView, Long> {
    List<ProfileView> findAllBySurname(String surname);
    List<ProfileView> findAllByName(String name);
}
