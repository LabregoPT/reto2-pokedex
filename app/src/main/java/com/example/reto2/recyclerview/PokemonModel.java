package com.example.reto2.recyclerview;

import com.example.reto2.model.Entrenador;

import java.io.Serializable;

public class PokemonModel implements Serializable {

    private String id;
    private String name;
    private String ataque;
    private String vida;
    private String defensa;
    private String velocidad;
    private String tipo;
    private String imagen;
    private Entrenador entrenador;



    public PokemonModel() {
    }

    public PokemonModel(String id, String name, String ataque, String vida, String defensa, String velocidad, String tipo, String imagen) {
        this.id = id;
        this.name = name;
        this.ataque = ataque;
        this.vida = vida;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.tipo = tipo;
        this.imagen = imagen;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAtaque() {
        return ataque;
    }

    public void setAtaque(String ataque) {
        this.ataque = ataque;
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getDefensa() {
        return defensa;
    }

    public void setDefensa(String defensa) {
        this.defensa = defensa;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
