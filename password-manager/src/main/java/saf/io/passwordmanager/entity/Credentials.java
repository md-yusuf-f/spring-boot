package saf.io.passwordmanager.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import saf.io.passwordmanager.model.CredentialsRequest;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credentials {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long crdId;

  private String accountName;

  private String userName;

  private String password;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @CreationTimestamp private Timestamp createdAt;

  @UpdateTimestamp private Timestamp updatedAt;

  public static Credentials from(User user, CredentialsRequest request) {
    return Credentials.builder()
        .accountName(request.getAccountName())
        .userName(request.getUserName())
        .password(request.getPassword())
        .user(user)
        .build();
  }
}
