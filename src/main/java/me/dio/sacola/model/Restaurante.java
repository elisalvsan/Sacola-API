package me.dio.sacola.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor //cria um construtor com todos os atributos
@Builder //produz APIs de construtor complexas para as classes
@Data // sreve para gerar gets and sets entre outras coisas(tostring, hashcode,equals)
@Entity // indica que e uma tabela
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"}) // usado para nao ocorrer erro ao se utilizar JsonIgnore
@NoArgsConstructor // cria um construtor vazio
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Produto> cardapio;
    @Embedded
    private Endereco endereco;

}
