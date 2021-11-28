package com.sda.softwaretestingadvancedpractice;

import com.sda.softwaretestingadvancedpractice.utils.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Parameterized tests
 *
 * @author Vinod John
 */
public class UserValidatorParameterizedTest {
  @ParameterizedTest
  @ValueSource(strings = {"Vinod123456", "John123456", "AvengersAssemble2021", "sfbiuqbr8yg98rqibfiqubfp971bfpiqfiuqbwfu"})
  public void givenUserPassword_whenEncodePasswordCalled_shouldReturnEncodedPassword(String password) {
    String expectedValue = password.substring(0, password.length() / 2) + "#sda_java#" + password.substring(password.length() / 2);
    Assertions.assertEquals(expectedValue, new UserValidator().encodePassword(password));
  }

  @ParameterizedTest
  @MethodSource("getPasswords")
  public void givenUserPasswords_whenEncodePasswordCalled_shouldReturnEncodedPasswords(String password) {
    String expectedValue = password.substring(0, password.length() / 2) + "#sda_java#" + password.substring(password.length() / 2);
    Assertions.assertEquals(expectedValue, new UserValidator().encodePassword(password));
  }

  @ParameterizedTest
  @ArgumentsSource(Parameters.class)
  public void givenUserPasswordsFromProvider_whenEncodePasswordCalled_shouldReturnEncodedPassword(String password) {
    String expectedValue = password.substring(0, password.length() / 2) + "#sda_java#" + password.substring(password.length() / 2);
    Assertions.assertEquals(expectedValue, new UserValidator().encodePassword(password));
  }

  @ParameterizedTest
  @CsvSource({"Vinod123456, Vinod#sda_java#123456", "John123456, John1#sda_java#23456"})
  public void givenUserPasswordsFromCsv_whenEncodePasswordCalled_shouldReturnEncodedPassword(String password, String encodedPassword) {
    Assertions.assertEquals(encodedPassword, new UserValidator().encodePassword(password));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/dataSource.csv")
  public void givenUserPasswordsFromCsvFile_whenEncodePasswordCalled_shouldReturnEncodedPassword(String password, String encodedPassword) {
    Assertions.assertEquals(encodedPassword, new UserValidator().encodePassword(password));
  }

  @ParameterizedTest
  @ValueSource(strings = {"Vinod123456", "John123456", "AvengersAssemble2021", "sfbiuqbr8yg98rqibfiqubfp971bfpiqfiuqbwfu"})
  public void givenUserPasswordConverted_whenEncodePasswordCalled_shouldReturnEncodedPassword(
    @ConvertWith(PasswordConverter.class) String password) {
    String expectedValue = password.substring(0, password.length() / 2) + "#sda_java#" + password.substring(password.length() / 2);
    Assertions.assertEquals(expectedValue, new UserValidator().encodePassword(password));
  }

  //For method source
  static Stream<Arguments> getPasswords() {
    return Stream.of(Arguments.of("Vinod123456"), Arguments.of("John123456"));
  }

  //For Argument source
  static class Parameters implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
      return Stream.of(Arguments.of("Vinod123456"), Arguments.of("John123456"));
    }
  }

  //For ArgumentConverter
  static class PasswordConverter implements ArgumentConverter {

    @Override
    public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
      if (o instanceof String) {
        return ((String)o).replaceAll("12345", "67890");
      }

      fail("Cannot replace string");
      return null;
    }
  }
}
