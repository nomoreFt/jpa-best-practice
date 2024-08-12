package jpa.practice.relationship.sqlcount_assert.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ExtendWith(MockitoExtension.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface UnitTest {
}
