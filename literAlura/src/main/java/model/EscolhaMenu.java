package model;

import org.springframework.stereotype.Component;
import services.ConsumoApi;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class EscolhaMenu {

    private final ConsumoApi consumo;
    private final Scanner leitura = new Scanner(System.in);
    private final List<DadosLivro> livrosBuscados = new ArrayList<>();


    public EscolhaMenu(ConsumoApi consumo) {
        this.consumo = consumo;
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

        // O método agora retorna um Optional
        Optional<DadosLivro> livroOptional = consumo.obtemDados(titulo);

        // Verificamos se o Optional contém um valor
        if (livroOptional.isPresent()) {
            DadosLivro livroEncontrado = livroOptional.get();
            livrosBuscados.add(livroEncontrado); // Adiciona na sua lista local

            System.out.println("----- LIVRO ENCONTRADO -----");
            System.out.println("Título: " + livroEncontrado.titulo());

            if (livroEncontrado.autores() != null && !livroEncontrado.autores().isEmpty()) {
                System.out.println("Autor: " + livroEncontrado.autores().get(0).nome());
            } else {
                System.out.println("Autor: Desconhecido");
            }

            if (livroEncontrado.idiomas() != null && !livroEncontrado.idiomas().isEmpty()) {
                System.out.println("Idioma: " + livroEncontrado.idiomas().get(0));
            } else {
                System.out.println("Idioma: Desconhecido");
            }

            System.out.println("Downloads: " + livroEncontrado.numDownloads());
            System.out.println("--------------------------");

        } else {
            System.out.println("Nenhum livro encontrado com o título '" + titulo + "'!");
        }
    }

    private void listarLivrosRegistrados() {
        if (livrosBuscados.isEmpty()) {
            System.out.println("Nenhum livro registrado ainda.");
        } else {
            livrosBuscados.forEach(l -> System.out.println("Título: " + l.titulo()));
        }
    }

    private void listarAutoresRegistrados() {
        Set<String> autores = livrosBuscados.stream()
                .flatMap(l -> l.autores().stream())
                .map(Autor::nome)
                .collect(Collectors.toSet());

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado ainda.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Digite o ano para verificar autores vivos: ");
        int ano = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autoresVivos = livrosBuscados.stream()
                .flatMap(l -> l.autores().stream())
                .filter(a -> a.anoNascimento()!= null && a.anoNascimento() <= ano)
                .filter(a -> a.anoFalecimento() == null || a.anoFalecimento() >= ano)
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado vivo em " + ano);
        } else {
            autoresVivos.forEach(a -> System.out.println(a.nome() +
                    " (" + a.anoNascimento() + " - " +
                    (a.anoFalecimento() == null ? "..." : a.anoFalecimento()) + ")"));
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Digite o idioma (ex: 'en', 'pt'): ");
        String idioma = leitura.nextLine();

        List<DadosLivro> livrosPorIdioma = livrosBuscados.stream()
                .filter(l -> l.idiomas().contains(idioma))
                .toList();

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
        } else {
            livrosPorIdioma.forEach(l -> System.out.println("Título: " + l.titulo()));
        }

    }
}
