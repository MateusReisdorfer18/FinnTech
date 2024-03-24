package br.com.FinnTech;

import br.com.FinnTech.controller.BancoController;
import br.com.FinnTech.controller.ClienteController;
import br.com.FinnTech.controller.ContaController;
import br.com.FinnTech.controller.TipoContaController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ContaController contaController = new ContaController();
        ClienteController clienteController = new ClienteController();
        TipoContaController tipoContaController = new TipoContaController();
        BancoController bancoController = new BancoController();
        int opcao;

        do {
            System.out.println("""
                        Bem vindo! \s
                        Selecione uma opção \s
                        [1] Cliente \s
                        [2] Conta \s
                        [0] Sair \s
                    """);
            opcao = scan.nextInt();

            if(opcao == 1) {
                clienteController.chamarMenu(scan);
            } else if (opcao == 2) {
                contaController.chamarMenu(scan, clienteController, bancoController, tipoContaController);
            }
        } while(opcao != 0);
    }
}