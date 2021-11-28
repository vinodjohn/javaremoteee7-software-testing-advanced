package com.sda.softwaretestingadvancedpractice.services;

import com.sda.softwaretestingadvancedpractice.exceptions.ServiceUnavailableException;
import com.sda.softwaretestingadvancedpractice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Service to handle user related operations
 *
 * @author Vinod John
 */
@Service
public class UserService {
  @Autowired
  private RestTemplate restTemplate;

  public User getUserFromRemoteServer() throws ServiceUnavailableException {
    try {
      ResponseEntity<User> userResponseEntity = restTemplate.getForEntity("https://www.google.com/user", User.class);
      return userResponseEntity.getBody();
    } catch (HttpServerErrorException httpServerErrorException) {
      throw new ServiceUnavailableException("Server unavailable!");
    }
  }
}
