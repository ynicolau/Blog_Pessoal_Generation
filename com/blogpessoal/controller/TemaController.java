package com.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogpessoal.model.Tema;
import com.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/Temas")
@CrossOrigin("*")

public class TemaController {
	
	@Autowired
    private TemaRepository temaRepository;

    @GetMapping
    public ResponseEntity<List<Tema>> GetAll(){
        return ResponseEntity.ok(temaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tema> GetById(@PathVariable Long id) {
    // Resposta chamando o Tabela
        return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        //Metodo procure na lista de Postagem, e busque por Id, mapeie se existe o Id, se não responda Não encontrado
    }
    
    @GetMapping("/descrição/{descricao}")
    public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao) {

        return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
   
    }
    
    @PostMapping
    public ResponseEntity<Tema> post(@Valid @RequestBody Tema tema) {

        return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
    }
    
    @PutMapping
    public ResponseEntity<Tema> put(@Valid @RequestBody Tema tema){

        return temaRepository.findById(tema.getId()).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(tema)));
    }
    
}
