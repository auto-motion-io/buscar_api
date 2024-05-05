package org.motion.buscar_api.application.services;

import org.motion.buscar_api.application.dtos.AvaliacaoDTO.CreateAvaliacaoDTO;
import org.motion.buscar_api.application.dtos.AvaliacaoDTO.UpdateAvaliacaoDTO;
import org.motion.buscar_api.application.exception.RecursoNaoEncontradoException;
import org.motion.buscar_api.domain.entities.buscar.Avaliacao;
import org.motion.buscar_api.domain.repositories.buscar.IAvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private IAvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao buscarPorId(int id) {
        return avaliacaoRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Avaliacao não encontrada com o id: " + id));
    }

    public Avaliacao criar(CreateAvaliacaoDTO novaAvaliacao) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(novaAvaliacao.nota());
        avaliacao.setComentario(novaAvaliacao.comentario());
        avaliacao.setOficinaAvaliacao(novaAvaliacao.fkOficina());
        avaliacao.setUsuarioAvaliacao(novaAvaliacao.fkUsuario());
        return avaliacaoRepository.save(avaliacao);
    }

    public void deletar(int id) {
        avaliacaoRepository.deleteById(id);
    }

    public Avaliacao atualizar(int id, UpdateAvaliacaoDTO avaliacaoAtualizada) {
        Avaliacao avaliacao = buscarPorId(id);
        avaliacao.setNota(avaliacaoAtualizada.nota());
        avaliacao.setComentario(avaliacaoAtualizada.comentario());
        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> buscarPorOficina(int idOficina) {
        return avaliacaoRepository.findByIdAvaliacaoAndOficinaAvaliacao(idOficina);
    }

    public List<Avaliacao> buscarPorUsuario(int idUsuario) {
        return avaliacaoRepository.findByIdAvaliacaoAndUsuarioAvaliacao(idUsuario);
    }
}
