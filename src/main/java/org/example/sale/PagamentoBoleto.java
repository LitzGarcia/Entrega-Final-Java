package org.example.sale;

import java.util.UUID;

public class PagamentoBoleto implements MetodoPagamento{
    @Override
    public void pagar(double valor) {
        System.out.println("📄 Pagamento via Boleto gerado. Código: " + UUID.randomUUID());
    }
}
