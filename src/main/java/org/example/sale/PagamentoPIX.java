package org.example.sale;

import java.util.UUID;

public class PagamentoPIX implements MetodoPagamento{
    @Override
    public void pagar(double valor) {
        System.out.println("🔑 Pagamento realizado via PIX. Chave: " + UUID.randomUUID());
    }
}
