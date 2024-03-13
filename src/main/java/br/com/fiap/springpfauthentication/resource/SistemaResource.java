package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.entity.Sistema;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.SistemaRepository;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/sistema")
public class SistemaResource {
    @Autowired
    private SistemaRepository sistemRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @GetMapping
    public List<Sistema> findAll() {
        return sistemRepository.findAll();
    }
    @GetMapping(value = "/{id}")
    public Sistema findById(@PathVariable Long id) {
        return sistemRepository.findById(id).orElseThrow();
    }

    @Transactional
    @PostMapping
    public Sistema save(@RequestBody Sistema sistema) {
        return sistemRepository.save(sistema);
    }

    @GetMapping(value = "/{id}/responsaveis")
    public Set<Usuario> findResponsaveis(@PathVariable Long id) {
        Sistema sistema = sistemRepository.findById(id).orElseThrow();
        return sistema.getResponsaveis();
    }

    @Transactional
    @PostMapping(value = "/{id}/responsaveis")
    public Sistema addResponsavel(@PathVariable Long id, @RequestBody Usuario user) {
        Sistema sistema = sistemRepository.findById(id).orElseThrow();

        if (Objects.isNull(user)) return null;

        if (Objects.nonNull(user.getId())) {
            Usuario usuario = userRepository.findById(user.getId()).orElseThrow();
            sistema.getResponsaveis().add(usuario);
            return sistema;
        }
        sistema.getResponsaveis().add(user);
        return sistema;
    }
}
