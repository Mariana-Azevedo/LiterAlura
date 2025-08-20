package com.example.literAlura.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.literAlura.model.DadosLivro;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Service
public class ConsumoApi
{
    public Optional<DadosLivro> obtemDados(String titulo) throws IOException, InterruptedException {
        String tituloCodificado = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + tituloCodificado;

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Erro na API: Status Code " + response.statusCode());
            return Optional.empty(); // Retorna um Optional vazio em caso de erro
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode results = root.path("results"); // .path() é mais seguro que .get()

        // Verifica se a lista de resultados existe e não está vazia
        if (results.isArray() && !results.isEmpty()) {
            // Pega apenas o primeiro elemento da lista
            JsonNode primeiroLivroNode = results.get(0);
            DadosLivro livro = mapper.treeToValue(primeiroLivroNode, DadosLivro.class);
            return Optional.of(livro); // Retorna um Optional contendo o livro
        }

        return Optional.empty(); // Retorna um Optional vazio se não encontrou resultados
    }
}
