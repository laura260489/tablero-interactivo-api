package com.laurarojas.tablerointeractivoapi.repository;

import com.laurarojas.tablerointeractivoapi.entity.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<TaskEntity, String> {
}
