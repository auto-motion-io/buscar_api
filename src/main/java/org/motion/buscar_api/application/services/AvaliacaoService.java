package org.motion.buscar_api.application.services;

import org.motion.buscar_api.application.dtos.AvaliacaoDTO.CreateAvaliacaoDTO;
import org.motion.buscar_api.application.dtos.AvaliacaoDTO.UpdateAvaliacaoDTO;
import org.motion.buscar_api.application.exception.RecursoNaoEncontradoException;
import org.motion.buscar_api.domain.entities.buscar.Avaliacao;
import org.motion.buscar_api.domain.repositories.IOficinaRepository;
import org.motion.buscar_api.domain.repositories.buscar.IAvaliacaoRepository;
import org.motion.buscar_api.domain.repositories.buscar.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private IAvaliacaoRepository avaliacaoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IOficinaRepository oficinaRepository;

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

        avaliacao.setUsuarioAvaliacao(usuarioRepository.findById(novaAvaliacao.fkUsuario()).orElseThrow(() ->
                new RecursoNaoEncontradoException("Usuário não encontrado com o id: " + novaAvaliacao.fkUsuario())));

        avaliacao.setOficinaAvaliacao(oficinaRepository.findById(novaAvaliacao.fkOficina()).orElseThrow(() ->
                new RecursoNaoEncontradoException("Oficina não encontrada com o id: " + novaAvaliacao.fkOficina())));

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
}
