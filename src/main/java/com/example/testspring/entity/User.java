package com.example.testspring.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//ORM framework: Object - Record table
// JPA - Hibernate
// mình sẽ không dùng JDBC SQL nữa
@Data //mình dùng cái này để có thể muốn generate constructor với get and set method
@Table (name = "user") //map to table SQL
@Entity //BEAN new project
@EqualsAndHashCode(callSuper = true)
public class User extends TimeAuditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @OneToMany(mappedBy = "user")
//    private List<UserRole> roles;
    // có thể dugnf cách khác như sau
    //chỉ áp dụng với bảng chỉ 2 cột ,1 cột FK

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    private String name;
    //luu ten filePath
    private String avatarURL;

    private int age;

    @Column(unique = true)
    private String username;

    private String password;
//    Gia su mac dinh name trong table sql se la (home_address)
// KHONG CAN DAT NAME
    private String homeAddress;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(unique = true)

    private String email;

    @Column(unique = true)
    private String phoneNumber;

}
