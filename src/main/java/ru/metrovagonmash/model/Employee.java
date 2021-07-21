package ru.metrovagonmash.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    // FIXME: 19.07.2021 Проверить
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profileId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department departmentId;

/*    @Override
    public Long getId() {
        return id;
    }*/
}
