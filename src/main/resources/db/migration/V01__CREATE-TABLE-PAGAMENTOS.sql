CREATE TABLE pagamentos(
    id SERIAL PRIMARY KEY NOT NULL,
    valor DECIMAL(19, 2) NOT NULL,
    nome_usuario VARCHAR(169) DEFAULT NULL,
    numero VARCHAR(19) DEFAULT NULL,
    expiracao VARCHAR(7) DEFAULT NULL,
    codigo VARCHAR(3) DEFAULT NULL,
    status VARCHAR(256) NOT NULL,
    pedido_id BIGINT NOT NULL,
    forma_pagamento_id SERIAL NOT NULL
);