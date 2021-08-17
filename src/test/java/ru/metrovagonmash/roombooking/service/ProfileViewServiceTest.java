/*
package ru.metrovagonmash.roombooking.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import ru.metrovagonmash.model.Employee;
import ru.metrovagonmash.model.Profile;
import ru.metrovagonmash.model.ProfileView;
import ru.metrovagonmash.roombooking.RoomBookingApplication;
import ru.metrovagonmash.service.ProfileViewService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ContextConfiguration(classes = {RoomBookingApplication.class})
public class ProfileViewServiceTest {

    @Autowired
    private ProfileViewService profileViewService;

    @Autowired
    private TestEntityManager testEntityManager;

    private List<ProfileView> profileViewList;
    private List<Employee> employeeList;
    private List<Profile> profileList;

    @BeforeEach
    void init() {
        profileViewList = new ArrayList<>();

        profileViewList.add(ProfileView.builder()
                .banned(true)
                .email("test@gmail.com")
                .phone("4343424")
                .middleName("Lol")
                .surname("Test")
                .name("Carl")
                .id(1L)
                .build());
        profileViewList.add(ProfileView.builder()
                .banned(true)
                .email("test@gmail.com")
                .phone("4343424")
                .middleName("Lol")
                .surname("Test")
                .name("Carl")
                .id(2L)
                .build());
        employeeList = profileViewList.stream().map(this::toEmployee).collect(Collectors.toList());
        profileList = profileViewList.stream().map(this::toProfile).collect(Collectors.toList());
        employeeList.forEach(x -> testEntityManager.persist(x));
        profileList.forEach(x->testEntityManager.persist(x));


    }

    @Test
    void findAll_thenReturnOk() {
        List<ProfileView> list = profileViewService.findAll();
        assertEquals(profileViewList,list);
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
}
*/
