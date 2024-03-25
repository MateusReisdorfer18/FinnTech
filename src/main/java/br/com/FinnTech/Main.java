package br.com.FinnTech;

import br.com.FinnTech.controller.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ContaController contaController = new ContaController();
        ClienteController clienteController = new ClienteController();
        TipoContaController tipoContaController = new TipoContaController();
        BancoController bancoController = new BancoController();
        TipoPagamentoController tipoPagamentoController = new TipoPagamentoController();
        PagamentoController pagamentoController = new PagamentoController();
        int opcao;

        do {
            System.out.println("""
                        Bem vindo! \s
                        Selecione uma opção \s
                        [1] Cliente \s
                        [0] Sair \s
                    """);
            opcao = scan.nextInt();

            if(opcao == 1) {
                clienteController.chamarMenu(scan, contaController, tipoPagamentoController, pagamentoController, clienteController, bancoController, tipoContaController);
            }
        } while(opcao != 0);

        System.out.println("Só um comentario pro poder arrumar o commit");
    }
}