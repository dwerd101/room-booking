package ru.metrovagonmash.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profile_view")
@Immutable
@Builder
@Subselect("select profile.id, employee.name, employee.surname, employee.middle_name, employee.phone," +
        " employee.email, profile.account_non_locked as banned from employee inner join profile" +
        " on employee.profile_id = profile.id")
public class ProfileView {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name ="name")
    private String name;
    @Column(name ="surname")
    private String surname;
    @Column(name ="middle_name")
    private String middleName;
    @Column(name ="phone")
    private String  phone;
    @Column(name ="email")
    private String email;
    @Column(name = "banned")
    private Boolean banned;



    @Override
    public int hashCode() {
        return 13;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProfileView other = (ProfileView) obj;
        if (id == null) {
            return false;
        } else return id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "ProfileView{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", middleName='" + middleName + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", banned=" + banned +
            '}';
    }
}
