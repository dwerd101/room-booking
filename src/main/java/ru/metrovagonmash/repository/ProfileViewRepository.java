package ru.metrovagonmash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.ProfileView;
@Repository
public interface ProfileViewRepository extends JpaRepository<ProfileView, Long> {
}
