package ru.metrovagonmash.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "department")
/*@SequenceGenerator(
        name = "seqid-gen",
        sequenceName = "SEQ_GEN", initialValue = 1, allocationSize = 1)*/
public class Department {
    @Id
   // @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seqid-gen" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_department")
    private String nameDepartment;
    @Column(name = "position")
    private String position;

/*    @Override
    public Long getId() {
        return id;
    }*/

   /* @Override
    public boolean isNew() {
        return false;
    }*/
}
