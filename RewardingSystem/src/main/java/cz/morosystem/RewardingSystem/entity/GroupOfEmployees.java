package cz.morosystem.RewardingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "group_of_employees")
public class GroupOfEmployees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_of_employees_id")
    private long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", nullable = false)
    private Employee creator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employee_group",
            joinColumns = @JoinColumn(name = "group_of_employees_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Role> members = new ArrayList<>();
}
