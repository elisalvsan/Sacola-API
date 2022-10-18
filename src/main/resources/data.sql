INSERT INTO restaurante (id, cep, complemento, nome) VALUES
(1L, '58020500', 'R. Dep. Odon Bezerra, 184 - Tambia', 'Burguer King Tambia'),
(2L, '58037900', 'Av. Gov. Flavio Ribeiro Coutinho, 805 - Manaira', 'McDonalds');

INSERT INTO cliente (id, cep, complemento, nome) VALUES
(1L, '58096254', 'Complemento Endereco Cliente 1', 'Ana Julia Toscano'),
(2L, '58096254', 'Complemento Endereco Cliente 2', 'Fernando Justino');

INSERT INTO produto (id, disponivel, nome, valor_unitario, restaurante_id) VALUES
(1L, true, 'Combo Milanesa 1.0', 28.90, 1L),
(2L, true, 'Shake', 13.90, 1L),
(3L, true, 'Duplo Quarterao', 33.90, 2L);

INSERT INTO sacola (id, forma_pagamento, fechada, valor_total, cliente_id) VALUES
(1L, 0, false, 0.0, 1L),
(2L, 1, false, 0.0, 2L);
