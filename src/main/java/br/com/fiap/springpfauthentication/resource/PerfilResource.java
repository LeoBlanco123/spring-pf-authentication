package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.entity.Perfil;
import br.com.fiap.springpfauthentication.entity.Permissao;
import br.com.fiap.springpfauthentication.repository.PerfilRepository;
import br.com.fiap.springpfauthentication.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/perfil")
public class PerfilResource {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PermissaoRepository permRepository;

    @GetMapping
    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }
    @GetMapping(value = "/{id}")
    public Perfil findById(@PathVariable Long id) {
        return perfilRepository.findById(id).orElseThrow();
    }

    @Transactional
    @PostMapping
    public Perfil save(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @GetMapping(value = "/{id}/permissoes")
    public Set<Permissao> findPermissoes(@PathVariable Long id) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow();
        return perfil.getPermissoes();
    }

    @Transactional
    @PostMapping(value = "/{id}/permissoes")
    public Perfil addPermissao(@PathVariable Long id, @RequestBody Permissao perm) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow();

        if(Objects.isNull(perm)) return null;

        if(Objects.nonNull(perm.getId())){
            Permissao permissao = permRepository.findById(perm.getId()).orElseThrow();
            perfil.getPermissoes().add(permissao);
            return perfil;
        }
        perfil.getPermissoes().add(perm);

        return perfil;
    }
}
