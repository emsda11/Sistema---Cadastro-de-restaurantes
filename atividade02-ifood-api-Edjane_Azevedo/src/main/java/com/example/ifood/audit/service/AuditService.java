package com.example.ifood.audit.service;

import com.example.ifood.audit.model.LogAuditoria;
import com.example.ifood.audit.repository.LogAuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final LogAuditoriaRepository repository;

    public AuditService(LogAuditoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional("auditTransactionManager")
    public void registrar(String entidade, Long recursoId, String operacao) {
        LogAuditoria log = new LogAuditoria(entidade, recursoId, operacao, LocalDateTime.now());
        repository.save(log);
    }
}
