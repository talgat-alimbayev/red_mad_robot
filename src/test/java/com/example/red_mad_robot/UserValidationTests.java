package com.example.red_mad_robot;

import com.example.red_mad_robot.models.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserValidationTests {
    private static Validator validator;
    @BeforeAll
    static void setUp(){
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds(){
        var user = new User("example@email.com", "password");
        Set<ConstraintViolation<User>> violations =
                validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenEmailMissingThenValidationFails(){
        var user = new User("", "password");
        Set<ConstraintViolation<User>> violations =
                validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("email не может быть пустым");
    }
}
