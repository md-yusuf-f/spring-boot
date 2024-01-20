package saf.io.passwordmanager.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saf.io.passwordmanager.entity.Credentials;
import saf.io.passwordmanager.entity.User;
import saf.io.passwordmanager.model.CredentialsRequest;
import saf.io.passwordmanager.model.UserRequest;
import saf.io.passwordmanager.repository.CredentialsRepository;
import saf.io.passwordmanager.repository.UserRepository;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  private final CredentialsRepository credentialsRepository;

  @Autowired
  public UserService(UserRepository userRepository, CredentialsRepository credentialsRepository) {
    this.userRepository = userRepository;
    this.credentialsRepository = credentialsRepository;
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public void addUser(UserRequest request) {
    userRepository.save(User.from(request));
  }

  public void updateUser(String userId, UserRequest userRequest) {
    User user = userRepository.findById(Long.valueOf(userId)).orElse(null);
    userRepository.save(User.from(user, userRequest));
  }

  public void addPassword(String userId, CredentialsRequest credentialsRequest) {
    User user = userRepository.findById(Long.valueOf(userId)).orElse(null);

    credentialsRequest.setPassword(
        encryptPassword(
            credentialsRequest.getPassword(),
            new String(Base64.getDecoder().decode(user.getSecretKey()))));

    credentialsRepository.save(Credentials.from(user, credentialsRequest));
  }

  private String encryptPassword(String password, String secretKey) {
    try {
      var key = new SecretKeySpec(secretKey.getBytes(), "AES");
      var cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      var encryptedBytes = cipher.doFinal(password.getBytes());
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (NoSuchPaddingException
        | IllegalBlockSizeException
        | NoSuchAlgorithmException
        | BadPaddingException
        | InvalidKeyException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public void deleteUser(String userId) {
    userRepository.deleteById(Long.valueOf(userId));
    credentialsRepository.deleteByUserId(Long.valueOf(userId));
  }
}
