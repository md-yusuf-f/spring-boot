package saf.io.passwordmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saf.io.passwordmanager.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long> {
  void deleteByUserId(Long id);
}
