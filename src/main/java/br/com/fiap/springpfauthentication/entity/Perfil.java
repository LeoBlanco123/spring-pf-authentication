package br.com.fiap.springpfauthentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TB_2PF_SIMU_PERFIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PERFIL")
    @SequenceGenerator(
            name = "SQ_PERFIL",
            sequenceName = "SQ_PERFIL",
            initialValue = 1,
            allocationSize = 1
    )
    @Column(name = "ID_PERFIL")
    private Long id;

    @Column(name = "NM_PERFIL")
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "TB_2PF_SIMU_PERMISSOES",
            joinColumns = {
                    @JoinColumn(
                            name = "PERFIL",
                            referencedColumnName = "ID_PERFIL",
                            foreignKey = @ForeignKey(
                                    name = "FK_PERMISSOES_PERFIL"
                            )
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "PERMISSAO",
                            referencedColumnName = "ID_PERMISSAO",
                            foreignKey = @ForeignKey(
                                    name = "FK_PERFIL_PERMISSOES"
                            )
                    )
            }
    )
    private Set<Permissao> permissoes = new LinkedHashSet<>();
}
