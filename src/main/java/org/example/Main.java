package org.example;
import org.example.database.Conexao;
import org.example.database.DatabaseInitializer;
import org.example.entities.Product;
import org.example.entities.Sale;
import org.example.entities.User;
import org.example.enums.FormaPagamento;
import org.example.repository.ProductRepository;
import org.example.repository.SaleRepository;
import org.example.repository.UserRepository;
import org.example.sale.MetodoPagamento;
import org.example.sale.PagamentoFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Connection conn = Conexao.conectar();
        if (conn != null) {
            //System.out.println("🟢 Tudo certo com a conexão!");
            DatabaseInitializer.init(conn);
        } else {
            //System.out.println("🔴 Falha na conexão!");
        }

        UserRepository userRepository = new UserRepository();

        // Criando um novo usuário


        // Criando usuário de teste para o professor
        System.out.println("\nCriando usuário: NOME: João da Silva EMAIL: rafael@example.com SENHA: 123  \n");
        Optional<User> existing = userRepository.findByEmail("rafael@example.com");
        if (existing.isEmpty()) {
            User exemploProfessor = new User("João da Silva", "rafael@example.com", "123");
            userRepository.save(exemploProfessor);
            System.out.println("👤 Usuário exemplo (rafael@example.com) criado.");
        }

        ProductRepository productRepository = new ProductRepository();

        UUID mouseGamerId = UUID.nameUUIDFromBytes("mouseGamer".getBytes());
        if (productRepository.findById(mouseGamerId).isEmpty()) {
            Product newProduct = new Product(mouseGamerId, "Mouse Gamer", 129.90, 10);
            productRepository.save(newProduct);
            System.out.println("🖱️ Produto 'Mouse Gamer' criado.");
        }

        // Criando produtos exemplo com UUID fixo
        UUID camisetaId = UUID.nameUUIDFromBytes("camiseta".getBytes());
        UUID tenisId = UUID.nameUUIDFromBytes("tenis".getBytes());

        if (productRepository.findById(camisetaId).isEmpty()) {
            Product camiseta = new Product(camisetaId, "Camiseta", 50.00, 10);
            productRepository.save(camiseta);
        }

        if (productRepository.findById(tenisId).isEmpty()) {
            Product tenis = new Product(tenisId, "Tênis", 200.00, 10);
            productRepository.save(tenis);
        }

        List<Product> products = productRepository.findAll();
        System.out.println("\n📦 Lista de produtos:");
        for (Product p : products) {
            System.out.println(p.getUuid() + " - " + p.getName() + " - R$" + p.getPrice() + " - Quantidade: " + p.getQuantity());
        }
        System.out.println("\n\n");




        Scanner scanner = new Scanner(System.in);
        UserRepository userRepo = new UserRepository();
        ProductRepository productRepo = new ProductRepository();
        SaleRepository saleRepo = new SaleRepository();

        // 1. Buscar usuário
        System.out.print("Digite o e-mail do usuário: ");
        String email = scanner.nextLine();
        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            System.out.println("❌ Usuário não encontrado.");
            return;
        }
        System.out.println("Usuário encontrado: " + user.getName());
        System.out.println("\n");

        // 2. Buscar produtos
        System.out.print("Digite os UUIDs dos produtos (separados por vírgula): ");
        String[] ids = scanner.nextLine().split(",");
        List<Product> selectedProducts = new ArrayList<>();

        for (String id : ids) {
            Product product = productRepo.findById(UUID.fromString(id.trim())).orElse(null);

            if (product != null) {
                selectedProducts.add(product);
            } else {
                System.out.println("⚠️ Produto não encontrado: " + id.trim());
            }
        }

        if (selectedProducts.isEmpty()) {
            System.out.println("❌ Nenhum produto válido selecionado.");
            return;
        }

        System.out.println("Produtos selecionados:");
        double total = 0;
        for (Product p : selectedProducts) {
            System.out.println("- " + p.getName() + " (R$ " + p.getPrice() + ")");
            total += p.getPrice();
        }

        // 3. Escolher forma de pagamento
        System.out.println("\nEscolha a forma de pagamento:");
        System.out.println("1 - Cartão de Crédito");
        System.out.println("2 - Boleto");
        System.out.println("3 - PIX");
        System.out.print("Opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());
        System.out.println("\n");

        FormaPagamento formaPagamento;
        switch (opcao) {
            case 1 -> formaPagamento = FormaPagamento.CARTAO;
            case 2 -> formaPagamento = FormaPagamento.BOLETO;
            case 3 -> formaPagamento = FormaPagamento.PIX;
            default -> {
                System.out.println("❌ Opção inválida.");
                return;
            }
        }

        // 4. Executar pagamento
        MetodoPagamento metodo = PagamentoFactory.criar(formaPagamento);
        System.out.println("Aguarde, efetuando pagamento...");
        metodo.pagar(total);

        // 5. Registrar venda
        List<UUID> productIds = selectedProducts.stream()
                .map(Product::getUuid)
                .toList();

        Sale sale = new Sale(user.getUuid(), productIds, total, formaPagamento.name());
        saleRepo.save(sale);

        // 6. Exibir resumo
        System.out.println("\nResumo da venda:");
        System.out.println("Cliente: " + user.getName());
        System.out.println("Produtos:");
        for (Product p : selectedProducts) {
            System.out.println("- " + p.getName());
        }
        System.out.println("Valor total: R$ " + total);
        System.out.println("Pagamento: " + formaPagamento.name());
    }
}
