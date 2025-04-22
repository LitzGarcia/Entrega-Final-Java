package org.example.sale;

import org.example.enums.FormaPagamento;

public class PagamentoFactory {
    public static MetodoPagamento criar(FormaPagamento forma) {
        return switch (forma) {
            case CARTAO -> new PagamentoCartao();
            case BOLETO -> new PagamentoBoleto();
            case PIX -> new PagamentoPIX();
        };
    }
}

