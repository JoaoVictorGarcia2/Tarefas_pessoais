package com.joao.tarefas.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.joao.tarefas.api.dto.TarefaDTO;
import com.joao.tarefas.model.entity.Tarefa;
import com.joao.tarefas.service.TarefaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*") 
public class TarefaResource {

    private final TarefaService service;

    public TarefaResource(TarefaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody TarefaDTO dto) {
        try {
            Tarefa tarefa = converterDTOparaEntidade(dto);
            Tarefa tarefaSalva = service.salvar(tarefa);
            return new ResponseEntity(tarefaSalva, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody TarefaDTO dto) {
        try {
            Tarefa tarefaExistente = service.obterPorId(id);
            
            Tarefa tarefaAtualizada = converterDTOparaEntidade(dto);
            tarefaAtualizada.setId(id);
            tarefaAtualizada.setDataCriacao(tarefaExistente.getDataCriacao());
            tarefaAtualizada.setDataAtualizacao(LocalDateTime.now());
            
            Tarefa tarefaSalva = service.atualizar(tarefaAtualizada);
            return ResponseEntity.ok(tarefaSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity listar() {
        try {
            List<Tarefa> tarefas = service.listar();
            return ResponseEntity.ok(tarefas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity obterPorId(@PathVariable Long id) {
        try {
            Tarefa tarefa = service.obterPorId(id);
            return ResponseEntity.ok(tarefa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Tarefa converterDTOparaEntidade(TarefaDTO dto) {
        return Tarefa.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .status(dto.getStatus())
                .observacoes(dto.getObservacoes())
                .dataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();
    }
}