package ru.metrovagonmash.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.model.dto.EmployeeDTO;
import ru.metrovagonmash.repository.ProfileViewRepository;
import ru.metrovagonmash.service.RoomService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileViewServiceImpl implements RoomService<ProfileView,Long> {

    private final JdbcTemplate jdbcTemplate;
    private final ProfileViewRepository profileViewRepository;

    @Transactional
    @Override
    public ProfileView save(ProfileView model) {
        return profileViewRepository.save(model);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfileView> findAll() {
        return profileViewRepository.findAll();
    }

    @Transactional
    public void batchUpdateProfileAndEmployee(List<ProfileView> profileViewList) {

        List<Employee> employeeList = profileViewList.stream()
                .map(this::toEmployee)
                .collect(Collectors.toList());

        List<Profile> profileList = profileViewList.stream()
                .map(this::toProfile)
                .collect(Collectors.toList());

        batchUpdateProfileAndEmployee(profileList,employeeList);
    }

    private Employee toEmployee(ProfileView profileView) {
        return Employee.builder()
                .name(profileView.getName())
                .middleName(profileView.getMiddleName())
                .surname(profileView.getSurname())
                .phone(profileView.getPhone())
                .email(profileView.getEmail())
                .profileId(Profile.builder().id(profileView.getId()).build())
                .build();
    }
    private Profile toProfile(ProfileView profileView) {
        return Profile.builder()
                .id(profileView.getId())
                .accountNonLocked(profileView.getBanned())
                .build();
    }
    private void batchUpdateProfileAndEmployee(List<Profile> profileList, List<Employee> employeeList) {
        jdbcTemplate.batchUpdate("" +
                        "update employee set email=?, name=?, middle_name=?, surname=?, phone=? where profile_id=?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1,employeeList.get(i).getEmail());
                        ps.setString(2,employeeList.get(i).getName());
                        ps.setString(3,employeeList.get(i).getMiddleName());
                        ps.setString(4,employeeList.get(i).getSurname());
                        ps.setString(5,employeeList.get(i).getPhone());
                        ps.setLong(6,employeeList.get(i).getProfileId().getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return employeeList.size();
                    }
                });

        jdbcTemplate.batchUpdate("update profile set account_non_locked = ? where id=?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        // Здесь можно увидеть работу транзации, если мы раскоментируем.
                        //  ps.setLong(1,4);
                        ps.setBoolean(1,profileList.get(i).getAccountNonLocked());
                        ps.setLong(2,profileList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return profileList.size();
                    }
                });
    }

}
