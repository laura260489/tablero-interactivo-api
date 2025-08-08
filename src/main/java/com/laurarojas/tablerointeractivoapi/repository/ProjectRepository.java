package com.laurarojas.tablerointeractivoapi.repository;

import com.laurarojas.tablerointeractivoapi.entity.ProjectEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {
}