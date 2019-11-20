INSERT INTO categoria (id, nome) VALUES(1, 'Salgados');
INSERT INTO categoria (id, nome) VALUES(2, 'Doces');
INSERT INTO categoria (id, nome) VALUES(3, 'Pães');
INSERT INTO categoria (id, nome) VALUES(4, 'Bolos');


INSERT INTO cliente (id, cpf, email, nome, senha) VALUES(1, '698.112.913-22', 'luciabc@tiger.com', 'Lúcia Benedita Cardoso', 'rfq6b2Sc5h');
INSERT INTO cliente (id, cpf, email, nome, senha) VALUES(2, '942.026.773-31', 'gustavo@vitacard.com.br', 'Gustavo Victor Ricardo Ferreira', 'CuxsdiEBLJ');
INSERT INTO cliente (id, cpf, email, nome, senha) VALUES(3, '231.794.256-79', 'caiomarcos@roche.com', 'Caio Marcos João Ramos', 'nUguEdLQD2');
INSERT INTO cliente (id, cpf, email, nome, senha) VALUES(4, '220.888.474-48', 'deboraisadora@girocenter.com.br', 'Débora Isadora Silveira', 'Nm2PgsJ6bj');


INSERT INTO empresa (id, cnpj, email, nome_fantasia, senha) VALUES(1, '96.561.442/0001-86', 'contact@acrestaurante.com.br', 'Alícia e Cauã Restaurante ME', 'CuxsdiEBLJ');
INSERT INTO empresa (id, cnpj, email, nome_fantasia, senha) VALUES(2, '36.112.464/0001-37', 'contact@saborcaseiro.com.br', 'Sabor Caseiro', 'Nm2PgsJ6bj');
INSERT INTO empresa (id, cnpj, email, nome_fantasia, senha) VALUES(3, '24.667.907/0001-77', 'contact@doceSabor.com.br', 'Doce Sabor', 'rfq6b2Sc5h');


INSERT INTO endereco (id, bairro, cep, cidade, complemento, logradouro, numero, uf, cliente_id)
            VALUES(1, 'Sítios de Recreio Gramado', '13101-626', 'Campinas', null, 'Alameda das Espatódeas', '459', 'SP', 1);
INSERT INTO endereco (id, bairro, cep, cidade, complemento, logradouro, numero, uf, cliente_id)
            VALUES(2, 'Jardim Indianópolis', '13050-214', 'Campinas', null, 'Avenida Tancredo Neves', '564', 'SP', 2);
INSERT INTO endereco (id, bairro, cep, cidade, complemento, logradouro, numero, uf, cliente_id)
            VALUES(3, 'Vila Joaquim Inácio', '13045-630', 'Campinas', null, 'Rua José Soriano de Sousa Filho', '533', 'SP', 3);
INSERT INTO endereco (id, bairro, cep, cidade, complemento, logradouro, numero, uf, cliente_id)
            VALUES(4, 'Barão Geraldo', '13084-175', 'Campinas', null, 'Rua Manoel Antunes Novo', '972', 'SP', 4);


INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(1, '2019-01-25', 2.50, 1);
INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(2, '2019-02-03', 2.5, 2);
INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(3, '2019-02-17', 0, 2);
INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(4, '2019-03-08', 0, 3);
INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(5, '2019-03-18', 0, 2);
INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(6, '2019-03-30', 0, 1);
INSERT INTO pedido (id, data, desconto, cliente_id) VALUES(7, '2019-04-17', 0, 1);


INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(1, 'Pão de cebola', 5.00, 3, 1);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(2, 'Pavê de chocolate branco', 15.00, 2, 1);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(3, 'Tapioca de frango', 5.00, 1, 3);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(4, 'Bolo de laranja', 25.00, 4, 2);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(5, 'Trufas', 3.00, 2, 2);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(6, 'Macarrão na chapa', 18.00, 1, 3);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(7, 'Pão de alho', 7.00, 3, 3);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(8, 'Bolo trufado de ninho', 50.00, 4, 1);
INSERT INTO produto (id, nome, valor, categoria_id, empresa_id) VALUES(9, 'Canjica', 4.50, 2, 2);


INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(2, 5.00, 1, 1);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(1, 45.00, 1, 8);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(1, 6.00, 2, 7);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(1, 25.00, 2, 4);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(2, 4.5, 2, 9);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(1, 50.00, 3, 8);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(3, 2.80, 4, 5);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(1, 3.00, 5, 5);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(2, 15.00, 5, 2);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(5, 5.00, 6, 3);
INSERT INTO itens_pedido (qtde, valor, pedido_id, produto_id) VALUES(2, 7.00, 7, 6);


INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(5, 'O melhor!', 7, 1);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(5, 'O melhor!', 5, 1);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(2, 'Comestivel!', 6, 2);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(4, 'Muito bom!', 7, 2);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(5, 'Uma delicia!', 3, 3);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(3, 'Mais ou menos!', 3, 4);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(2, 'Comestivel!', 2, 3);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(1, 'Ruim!', 1, 3);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(4, 'Muito bom!', 1, 4);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(4, 'Muito bom!', 5, 4);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(5, 'O melhor!', 1, 2);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(1, 'Ruim!', 9, 1);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(2, 'Comestivel!', 9, 3);
INSERT INTO classificacao (nota, obs, produto_id, cliente_id) VALUES(4, 'Boa!', 8, 2);


INSERT INTO telefone (cliente_id, telefones) VALUES(1, '(19) 2987-8860');
INSERT INTO telefone (cliente_id, telefones) VALUES(1, '(19) 98386-7407');
INSERT INTO telefone (cliente_id, telefones) VALUES(2, '(19) 2816-0927');
INSERT INTO telefone (cliente_id, telefones) VALUES(2, '(19) 99224-2974');
INSERT INTO telefone (cliente_id, telefones) VALUES(3, '(19) 2823-9740');
INSERT INTO telefone (cliente_id, telefones) VALUES(4, '(19) 98810-6645');
INSERT INTO telefone (cliente_id, telefones) VALUES(4, '(19) 2609-6973');