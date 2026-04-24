package com.example.ifood.audit.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_auditoria")
public class LogAuditoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entidade;
    private Long recursoId;
    private String operacao;
    private LocalDateTime dataHora;

    public LogAuditoria() {}
    public LogAuditoria(String entidade, Long recursoId, String operacao, LocalDateTime dataHora) {
        this.entidade = entidade; this.recursoId = recursoId; this.operacao = operacao; this.dataHora = dataHora;
    }
    public Long getId() { return id; }
    public String getEntidade() { return entidade; }
    public Long getRecursoId() { return recursoId; }
    public String getOperacao() { return operacao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setId(Long id) { this.id = id; }
    public void setEntidade(String entidade) { this.entidade = entidade; }
    public void setRecursoId(Long recursoId) { this.recursoId = recursoId; }
    public void setOperacao(String operacao) { this.operacao = operacao; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
