package org.motion.buscar_api.application.controllers.buscar;

import jakarta.validation.Valid;
import org.motion.buscar_api.application.dtos.AvaliacaoDTO.CreateAvaliacaoDTO;
import org.motion.buscar_api.application.dtos.AvaliacaoDTO.UpdateAvaliacaoDTO;
import org.motion.buscar_api.application.services.AvaliacaoService;
import org.motion.buscar_api.domain.entities.buscar.Avaliacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping
    public List<Avaliacao> listarTodos() {
        return avaliacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Avaliacao buscarPorId(@PathVariable int id) {
        return avaliacaoService.buscarPorId(id);
    }

    @PostMapping
    public Avaliacao criar(@RequestBody @Valid CreateAvaliacaoDTO novaAvaliacao) {
        return avaliacaoService.criar(novaAvaliacao);
    }

    @PutMapping("/{id}")
    public Avaliacao atualizar(@PathVariable int id, @RequestBody UpdateAvaliacaoDTO avaliacaoAtualizada) {
        return avaliacaoService.atualizar(id, avaliacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable int id) {
        avaliacaoService.deletar(id);
    }


}
