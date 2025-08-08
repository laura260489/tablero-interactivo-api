package com.laurarojas.tablerointeractivoapi.repository;

import com.laurarojas.tablerointeractivoapi.entity.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<TaskEntity, String> {
    
    @Query("SELECT t FROM TaskEntity t " +
           "JOIN t.board b " +
           "JOIN b.project p " +
           "WHERE p.name = :projectName")
    List<TaskEntity> findTasksByProjectName(@Param("projectName") String projectName);

    @Query("SELECT t FROM TaskEntity t " +
            "JOIN t.board b " +
            "WHERE b.name = :boardName")
    List<TaskEntity> findTasksByBoardName(@Param("boardName") String boardName);
}
