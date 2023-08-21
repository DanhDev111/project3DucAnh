//package com.example.testspring;
//
//import com.example.testspring.entity.Role;
//import com.example.testspring.entity.User;
//import com.example.testspring.repository.RoleRepo;
//import com.example.testspring.repository.UserRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.metrics.ApplicationStartup;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.transaction.Transactional;
//import java.lang.reflect.Array;
//import java.util.Arrays;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class DemoData implements ApplicationRunner {
//    @Autowired
//    RoleRepo roleRepo;
//
//    @Autowired
//    UserRepo userRepo;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        log.info("BEGIN INSERT ROLE DUMP");
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        if (roleRepo.findByName(role.getName())==null) {
//            try {
//                roleRepo.save(role);
//                log.info("INSERT DUMP" + role.getId());
//                User user = new User();
//                user.setUsername("sysadmin");
//                user.setPassword(new BCryptPasswordEncoder().encode("12345678"));
//                user.setName("SYS ADMIN");
//                user.setEmail("4BOOST@gmail.com");
//                user.setBirthDate(new Date());
//                user.setRoles(Arrays.asList(role));
//                userRepo.save(user);
//            } catch (Exception e) {
//
//            }
//        }
//    }
//}
