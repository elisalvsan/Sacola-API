package me.dio.sacola.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumeration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
//import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDTO;
import me.dio.sacola.service.SacolaService;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    // private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNaSacola(ItemDTO itemDTO) {
        Sacola sacola = verSacola(itemDTO.getSacolaId());

        if (sacola.isFechada()) {
            throw new RuntimeException("Esta sacola está fechada");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDTO.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDTO.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Esse produto não existe");
                        }))
                .build();

        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException(
                        "Não é possível adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        for (Item itemDaSacola : itensDaSacola) {
            double valorTotalItem = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                .sum();

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não esiste");
                });
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroformaPagamento) {

        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException("Inclua intens na sacola!");
        }

        FormaPagamento formaPagamento = numeroformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);

        return sacolaRepository.save(sacola);
    }

}
