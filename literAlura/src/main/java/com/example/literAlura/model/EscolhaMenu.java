package com.example.literAlura.model;

import com.example.literAlura.repository.AutorRepository;
import org.springframework.stereotype.Component;
import com.example.literAlura.repository.LivroRepository;
import com.example.literAlura.services.ConsumoApi;

import java.util.*;


@Component
public class EscolhaMenu {

    private final ConsumoApi consumo;

    private final LivroRepository livroRepository;
    private final Scanner leitura = new Scanner(System.in);
    private final AutorRepository autorRepository;



    public EscolhaMenu(ConsumoApi consumo, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.consumo = consumo;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void executarMenu() throws Exception {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
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

            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosPorAno();
                case 5 -> listarLivrosPorIdioma();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }


    private void buscarLivroPorTitulo() throws Exception {
        System.out.println("Digite o título do livro: ");
        var titulo = leitura.nextLine();
        Optional<DadosLivro> dadosLivroOpt = consumo.obtemDados(titulo);

        if (dadosLivroOpt.isPresent()) {
            DadosLivro dadosLivro = dadosLivroOpt.get();
            if (dadosLivro.autores() == null || dadosLivro.autores().isEmpty()) {
                System.out.println("Livro encontrado, mas sem autor associado.");
                return;
            }

            Autor dadosAutor = dadosLivro.autores().get(0);


            Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(dadosAutor.getNome());

            Autor autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {

                autor = new Autor(dadosAutor.getNome(), dadosAutor.getAnoNascimento(), dadosAutor.getAnoFalecimento());
                autorRepository.save(autor);
            }

            String idioma = !dadosLivro.idiomas().isEmpty() ? dadosLivro.idiomas().get(0) : "Desconhecido";
            Livro livro = new Livro(dadosLivro.titulo(), autor, idioma, dadosLivro.numDownloads().doubleValue());


            livroRepository.save(livro);


            System.out.println("---------------------------------");
            System.out.println("LIVRO SALVO COM SUCESSO!");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getNumDownloads());
            System.out.println("---------------------------------");


        } else {
            System.out.println("Nenhum livro encontrado!");
        }
    }

    private void listarLivrosRegistrados() {

        List<Livro> livrosDoBanco = livroRepository.findAll();

        if (livrosDoBanco.isEmpty()) {
            System.out.println("Nenhum livro registrado no banco de dados.");
        } else {
            System.out.println("----- LIVROS REGISTRADOS -----");

            livrosDoBanco.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor());
                System.out.println("Idioma: " + livro.getIdioma());
                System.out.println("Downloads: " + livro.getNumDownloads());
                System.out.println("------------------------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no banco de dados.");
        } else {
            System.out.println("----- AUTORES REGISTRADOS -----");
            autores.forEach(autor -> System.out.println(
                    "Autor: " + autor.getNome() +
                            " (Nascimento: " + autor.getAnoNascimento() +
                            " - Falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Vivo") + ")"
            ));
            System.out.println("-----------------------------");
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano para verificar autores vivos: ");
        int ano = leitura.nextInt();
        leitura.nextLine();


        List<Autor> autoresVivos = autorRepository.findAutoresVivosEmAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo em " + ano);
        } else {
            System.out.println("----- AUTORES VIVOS EM " + ano + " -----");
            autoresVivos.forEach(a -> System.out.println(
                    a.getNome() + " (Nascimento: " + a.getAnoNascimento() +
                            " - Falecimento: " + (a.getAnoFalecimento() == null ? "Vivo" : a.getAnoFalecimento()) + ")"
            ));
            System.out.println("---------------------------------");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
            Digite o idioma para busca:
            es - espanhol
            en - inglês
            fr - francês
            pt - português
            """);
        String idioma = leitura.nextLine();


        List<Livro> livrosPorIdioma = livroRepository.findByIdiomaContainingIgnoreCase(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma no banco de dados.");
        } else {
            System.out.println("----- LIVROS NO IDIOMA: " + idioma.toUpperCase() + " -----");

            livrosPorIdioma.forEach(livro -> {
                System.out.println("Título: " + livro.getTitulo());
                System.out.println("Autor: " + livro.getAutor().getNome());
                System.out.println("---------------------------------");
            });
        }
    }
}
