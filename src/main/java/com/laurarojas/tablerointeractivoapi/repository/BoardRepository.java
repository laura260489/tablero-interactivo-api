package com.laurarojas.tablerointeractivoapi.repository;

import com.laurarojas.tablerointeractivoapi.entity.BoardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<BoardEntity, String> {
}