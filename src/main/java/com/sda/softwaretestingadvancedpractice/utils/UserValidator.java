package com.sda.softwaretestingadvancedpractice.utils;

import com.sda.softwaretestingadvancedpractice.exceptions.UserValidationException;
import com.sda.softwaretestingadvancedpractice.models.User;
import com.sda.softwaretestingadvancedpractice.models.UserType;

/**
 * Validation utils for User
 *
 * @author Vinod John
 */
public class UserValidator {
  public boolean isAdminUser(User user) throws UserValidationException {
    String fullName = user.getFirstName() + " " + user.getLastName();

    if (user.getUserType() == null) {
      throw new NullPointerException(String.format("The user type is null for user:%s", fullName));
    }

    if (UserType.ADMIN.equals(user.getUserType())) {
      return true;
    }
    else {
      throw new UserValidationException(fullName, "User admin check has failed!");
    }
  }

  public String encodePassword(String password) {
    int midIndex = password.length() / 2;
    return password.substring(0, midIndex) + "#sda_java#" + password.substring(midIndex);
  }
}
