package ru.metrovagonmash.roombooking.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import ru.metrovagonmash.exception.DepartmentBadRequestException;
import ru.metrovagonmash.model.Department;
import ru.metrovagonmash.roombooking.RoomBookingApplication;
import ru.metrovagonmash.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {RoomBookingApplication.class})
@DataJpaTest
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private  TestEntityManager testEntityManager;

    private List<Department> departmentList;

    @BeforeEach
    void init() {
        departmentList = new ArrayList<>();
        departmentList.add(Department.builder()
                .id(1L)
                .nameDepartment("IT")
                .position("Admin")
                .build());
        departmentList.add(Department.builder()
                .id(2L)
                .nameDepartment("Management")
                .position("manager")
                .build());
        departmentList.add(Department.builder()
                .id(3L)
                .nameDepartment("Testing")
                .position("tester")
                .build());

        initDb();

      //  departmentList.forEach(x -> testEntityManager.persist(x));


    }
    @Test
    void findAll_thenReturnOk() {
        List<Department> departmentList = departmentService.findAll();
        assertNotEquals(0,departmentList.size());
    }
    @Test
    void save_thenReturnOk() {
        Department department = Department.builder()
                .nameDepartment("Games")
                .position("AquaMan")
                .build();
        assertEquals(department, departmentService.save(department));
    }
    @Test
    void update_thenReturnOk() {
        Department department = Department.builder()
                .nameDepartment("Testing")
                .position("tester2")
                .build();
        Long id = departmentService.findAll().get(2).getId();
        department.setId(id);
        Department resultDepartment = departmentService.update(department, id);
        assertEquals(department, resultDepartment);
    }
    @Test
    void deleteById_thenReturnOk() {
        Long id = departmentService.findAll().get(2).getId();
        Department departmentTemp =  departmentService.findAll().get(2);
      Department department =  departmentService.deleteById(id);
      assertEquals(departmentTemp, department);

    }
    @Test
    void deleteById_thenReturnFalse() {
        DepartmentBadRequestException departmentException =  assertThrows(DepartmentBadRequestException.class, () -> {
            departmentService.deleteById(6L);
        });
        String expetedMessage = "Не найден ID";
        String actualMessage = departmentException.getMessage();
        assertTrue(expetedMessage.contains(actualMessage));
    }
    @Test
    void findById_thenReturnOk() {
        Long id = departmentService.findAll().get(2).getId();
        Department departmentTemp =  departmentService.findAll().get(2);
        Department departmentResult =  departmentService.findById(id);
        assertEquals(departmentTemp, departmentResult);

    }
    @Test
    void findById_thenReturnFalse() {
        DepartmentBadRequestException departmentException = assertThrows(DepartmentBadRequestException.class, () -> {
            departmentService.findById(6L);
        });
        String expetedMessage = "Не найден ID";
        String actualMessage = departmentException.getMessage();
        assertTrue(expetedMessage.contains(actualMessage));
    }

    private void initDb() {
        testEntityManager.persist(Department.builder()
              //  .id(1L)
                .nameDepartment("IT")
                .position("Admin")
                .build());
        testEntityManager.persist(Department.builder()
             //   .id(2L)
                .nameDepartment("Management")
                .position("manager")
                .build());
        testEntityManager.persist(Department.builder()
              //  .id(2L)
                .nameDepartment("Testing")
                .position("tester")
                .build());
    }
}
