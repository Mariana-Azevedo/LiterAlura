package com.example.literAlura;

import model.EscolhaMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
		Scanner sc = new Scanner(System.in);
		int opcao = 0;

		while(true){
		System.out.println(
                """
                        ---------------------------------
                        Escolha o número de sua opção:
                        1 - Buscar livro pelo título
                        2 - Listar livros registrados
                        3 - Listar autores registrados
                        4 - Listar autores vivos em um determinado ano
                        5 - Listar livros em um determinado idioma
                        0 - Sair
                        ---------------------------------
                """);

			if(opcao == 0){
				System.out.println("Saindo do programa...");
				break;
			}


		}


		sc.close();


	}

}
