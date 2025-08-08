package com.laurarojas.tablerointeractivoapi.repository;

import com.laurarojas.tablerointeractivoapi.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String>  {
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findUserWithRolesByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
