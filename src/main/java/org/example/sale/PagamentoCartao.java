package org.example.sale;

import java.util.UUID;

public class PagamentoCartao  implements MetodoPagamento{
    @Override
    public void pagar(double valor) {
        System.out.println("💳 Pagamento confirmado com Cartão de Crédito. Autenticação: " + UUID.randomUUID());
    }
}
