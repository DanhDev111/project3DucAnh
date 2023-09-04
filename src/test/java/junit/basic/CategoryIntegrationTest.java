package junit.basic;


import com.example.testspring.TestSpringApplication;
import com.example.testspring.controller.CategoryController;
import com.example.testspring.dto.CategoryDTO;
import com.example.testspring.entity.Category;
import com.example.testspring.repository.CategoryRepo;
import com.example.testspring.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.NoResultException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestSpringApplication.class)
//@DataJpaTest
//@WebMvcTest(CategoryController.class)
public class CategoryIntegrationTest {
    @Autowired
    CategoryService categoryService;

    @Test
    public void createCategory(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("jUnitTest");
        categoryService.create(categoryDTO);

        CategoryDTO saveCategory = categoryService.getById(categoryDTO.getId());
        assertThat(categoryDTO.getName()).isEqualTo(saveCategory.getName());

        categoryService.delete(categoryDTO.getId());

        assertThrows(NoResultException.class,()->{
           categoryService.getById(categoryDTO.getId());
        });



    }
}
