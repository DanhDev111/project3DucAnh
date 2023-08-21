package com.example.testspring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

//ORM framework: Object - Record table
// JPA - Hibernate
// mình sẽ không dùng JDBC SQL nữa
@Data //mình dùng cái này để có thể muốn generate constructor với get and set method
public class UserDTO {
    //MAP
    //1.Att

    private int id;
    @NotBlank(message = "{not.blank}")
    private String name;

    private String avatarURL;

    @Min(value = 0)
    private int age;

    @NotBlank(message = "{not.blank}")
    @Size(min = 6, max = 15,message ="{size.password}")
    private String username;

    @Size(min = 8,max = 18,message ="{size.password}" )
    private String password;

    private String homeAddress;

    private String email;

    private String phoneNumber;

    private List<RoleDTO> roles;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date birthDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy",timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;



    //JsonIgnore Sẽ bỏ qua
    @JsonIgnore
    private MultipartFile file;


}
