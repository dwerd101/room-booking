package ru.metrovagonmash.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
/*@SequenceGenerator(
        name = "seqid-gen",
        sequenceName = "SEQ_GEN", initialValue = 1, allocationSize = 1)*/
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "phone")
    private String phone;

    @Email
    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private Boolean isActive;

    // Почититать merge all
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profileId;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department departmentId;

/*    @Override
    public Long getId() {
        return id;
    }*/
}
