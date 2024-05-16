package org.motion.buscar_api.application.services;

import org.motion.buscar_api.application.dtos.AvaliacaoDTO.CreateAvaliacaoDTO;
import org.motion.buscar_api.application.dtos.AvaliacaoDTO.UpdateAvaliacaoDTO;
import org.motion.buscar_api.application.exception.RecursoNaoEncontradoException;
import org.motion.buscar_api.application.services.util.ServiceHelper;
import org.motion.buscar_api.domain.entities.Oficina;
import org.motion.buscar_api.domain.entities.buscar.Avaliacao;
import org.motion.buscar_api.domain.entities.buscar.Usuario;
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
    private ServiceHelper serviceHelper;

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao buscarPorId(int id) {
        return avaliacaoRepository.findById(id).orElseThrow(() ->
                new RecursoNaoEncontradoException("Avaliacao não encontrada com o id: " + id));
    }

    public Avaliacao criar(CreateAvaliacaoDTO novaAvaliacao) {
        System.out.println(novaAvaliacao.fkOficina());
        Oficina oficina = serviceHelper.pegarOficinaValida(novaAvaliacao.fkOficina());
        Usuario usuario = serviceHelper.pegarUsuarioValido(novaAvaliacao.fkUsuario());
        Avaliacao avaliacao = new Avaliacao(novaAvaliacao, oficina, usuario);
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
