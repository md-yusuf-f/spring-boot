package saf.io.passwordmanager.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Base64;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import saf.io.passwordmanager.model.UserRequest;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_tbl")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userName;

  private String secretKey;

  @CreationTimestamp private Timestamp createdAt;

  @UpdateTimestamp private Timestamp updatedAt;

  public static User from(UserRequest request) {
    return User.builder()
        .userName(request.getUserName())
        .secretKey(Base64.getEncoder().encodeToString(request.getSecretKey().getBytes()))
        .build();
  }

  public static User from(User existing, UserRequest request) {
    existing.setUserName(request.getUserName());
    existing.setSecretKey(Base64.getEncoder().encodeToString(request.getSecretKey().getBytes()));
    return existing;
  }
}
