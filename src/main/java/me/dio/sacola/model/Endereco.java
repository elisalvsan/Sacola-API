package me.dio.sacola.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor //cria um construtor com todos os atributos
@Builder //produz APIs de construtor complexas para as classes
@Data // sreve para gerar gets and sets entre outras coisas(tostring, hashcode,equals)
@Embeddable //(Indica que a classe nao vai ser uma tabela do banco de dados)
@NoArgsConstructor // cria um construtor vazio
public class Endereco {

    private String cep;
    private String complemento;
}
