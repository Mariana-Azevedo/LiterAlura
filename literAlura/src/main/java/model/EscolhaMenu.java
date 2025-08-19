package model;

import java.util.Scanner;

public class EscolhaMenu {
    public void menu(int opcao) {
        Scanner sc = new Scanner(System.in);
        int ano = 0;
        switch (opcao) {
            case 1:
                System.out.println("Digite o titulo do livro que deseja buscar: ");
                String titulo = sc.nextLine();
                System.out.println("---------- LIVRO ----------\n" +
                        "Título: \n" +
                        "Autor: \n" +
                        "Idioma: \n" +
                        "Número de download: \n" +
                        "--------------------------");
                break;

            case 2:

                break;

            case 3:
                System.out.println("Autor:\n" +
                                    "Ano de nascimento:\n"+
                                    "Ano de falecimento:\n"+
                                    "Livros:\n");

                break;

            case 4:
                System.out.println("Insira o ano que deseja pesquisar: ");
                ano = sc.nextInt();
                break;

            case 5:
                System.out.println("Insira o idioma que deseja buscar: ");
                System.out.println("es - espanhol\n"+
                                    "en - inglês\n"+
                                    "fr - francês\n"+
                                    "pt - português\n");
                break;

        }

    }
}
