package com.example.ifood.audit.repository;
import com.example.ifood.audit.model.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {}
