package com.example.literAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idioma;

    private Double numDownloads;

    public Livro(String titulo, Autor autor, String idioma, Double numDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numDownloads = numDownloads;
    }

    public Livro() {

    }

    public Autor getAutor() { return autor; }

    public void setAutor(Autor autor) { this.autor = autor; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(Double numDownloads) {
        this.numDownloads = numDownloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
