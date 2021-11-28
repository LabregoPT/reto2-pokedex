package com.example.reto2.modelpokemonjson;

public class PokeApi {

    private int id;
    private String name;
    private Sprite sprites;
    private Stat[] stats;
    private Type[] types;

    public PokeApi() {
    }

    public PokeApi(int id, String name, Sprite sprites, Stat[] stats, Type[] types) {
        this.id = id;
        this.name = name;
        this.sprites = sprites;
        this.stats = stats;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sprite getSprites() {
        return sprites;
    }

    public void setSprites(Sprite sprites) {
        this.sprites = sprites;
    }

    public Stat[] getStats() {
        return stats;
    }

    public void setStats(Stat[] stats) {
        this.stats = stats;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }
}
