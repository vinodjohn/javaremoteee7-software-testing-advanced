package com.sda.softwaretestingadvancedpractice;

import com.sda.softwaretestingadvancedpractice.exceptions.UserValidationException;
import com.sda.softwaretestingadvancedpractice.models.User;
import com.sda.softwaretestingadvancedpractice.models.UserType;
import com.sda.softwaretestingadvancedpractice.utils.UserValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testing exceptions
 *
 * @author Vinod John
 */
public class UserValidatorTest {
  @Test
  public void givenUser_whenIsAdminUserCalled_shouldExpectException() {
    // Junit5 test examples
    try {
      User user = new User();
      user.setFirstName("Vinod");
      user.setLastName("John");
      user.setPassword("123456");
      user.setUserType(UserType.STANDARD);

      UserValidator userValidator = new UserValidator();
      userValidator.isAdminUser(user);
    }
    catch (UserValidationException e) {
      String expectedMessage = "User validation failed for user:Vinod John, Error:User admin check has failed!";
      assertEquals(expectedMessage, e.getLocalizedMessage());
    }
  }

  @Test
  public void givenUser_whenIsAdminUserCalledFunctionally_shouldExpectException() {
    User user = new User();
    user.setFirstName("Vinod");
    user.setLastName("John");
    user.setPassword("123456");
    user.setUserType(UserType.STANDARD);

    UserValidationException userValidationException = assertThrows(UserValidationException.class,
                                                                   () -> new UserValidator().isAdminUser(user));

    String expectedMessage = "User validation failed for user:Vinod John, Error:User admin check has failed!";
    assertEquals(expectedMessage, userValidationException.getLocalizedMessage());
  }

  //AssertJ Examples
  @Test
  public void givenUserWithoutUserType_whenIsAdminUserCalled_shouldExpectNPE(){
    User user = new User();
    user.setFirstName("Vinod");
    user.setLastName("John");
    user.setPassword("123456");

    Assertions.assertThatThrownBy(() -> new UserValidator().isAdminUser(user))
      .isExactlyInstanceOf(NullPointerException.class)
      .hasNoCause()
      .hasMessageContaining("null");
  }
}
