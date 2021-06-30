package ru.metrovagonmash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.metrovagonmash.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
}
