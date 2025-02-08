package com.dpm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author danielpm.dev
 */
public class ModeloVideojuegos {
    private ArrayList<Videojuego> listaVideojuegos;

    public ModeloVideojuegos() {
        listaVideojuegos = new ArrayList<>();

        listaVideojuegos.add(new Videojuego(1, "Call of Duty: Modern Warfare", "Activision", "Shooter", 16, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(2, "Call of Duty: Black Ops", "Activision", "Shooter", 18, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(3, "Crash Bandicoot N. Sane Trilogy", "Activision", "Platformer", 7, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(4, "The Legend of Zelda: Breath of the Wild", "Nintendo", "Adventure", 12, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(5, "Super Mario Odyssey", "Nintendo", "Platformer", 3, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(6, "Mario Kart 8 Deluxe", "Nintendo", "Racing", 3, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(7, "Minecraft", "Mojang Studios", "Sandbox", 7, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(8, "Minecraft Dungeons", "Mojang Studios", "Dungeon Crawler", 10, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(9, "Elden Ring", "FromSoftware", "RPG", 18, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(10, "Dark Souls III", "FromSoftware", "RPG", 18, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(11, "FIFA 23", "EA Sports", "Sports", 3, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(12, "Madden NFL 24", "EA Sports", "Sports", 3, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(13, "Grand Theft Auto V", "Rockstar Games", "Action", 18, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(14, "Red Dead Redemption 2", "Rockstar Games", "Adventure", 18, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(15, "Overwatch", "Blizzard Entertainment", "Shooter", 12, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(16, "Overwatch 2", "Blizzard Entertainment", "Shooter", 12, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(17, "Hollow Knight", "Team Cherry", "Metroidvania", 7, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(18, "Among Us", "Innersloth", "Party", 7, "src/main/resources/Caraturala.jpeg"));

        listaVideojuegos.add(new Videojuego(19, "The Last of Us", "Naughty Dog", "Action-Adventure", 18, "src/main/resources/Caraturala.jpeg"));
        listaVideojuegos.add(new Videojuego(20, "Uncharted 4: A Thief's End", "Naughty Dog", "Action-Adventure", 16, "src/main/resources/Caraturala.jpeg"));

    }

    public Videojuego getById(int id) {
        return listaVideojuegos.stream().
                filter(videojuego -> videojuego.getId() == id).
                findFirst().orElse(null);
    }

    public List<Videojuego> getByDesarrolladora(String desarrolladora) {
        return listaVideojuegos.stream().
                filter(videojuego -> videojuego.getDesarrolladora().
                        equalsIgnoreCase(desarrolladora)).collect(Collectors.toList());
    }

    public List<Videojuego> getByGenero(String genero) {
        return listaVideojuegos.stream().
                filter(videojuego -> videojuego.getGenero().
                        equalsIgnoreCase(genero)).collect(Collectors.toList());
    }

    public ArrayList<Videojuego> getListaVideojuegos() {
        return listaVideojuegos;
    }

    public void setListaVideojuegos(ArrayList<Videojuego> listaVideojuegos) {
        this.listaVideojuegos = listaVideojuegos;
    }
}
