package saf.io.passwordmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saf.io.passwordmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
