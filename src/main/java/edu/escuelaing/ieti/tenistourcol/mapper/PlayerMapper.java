package edu.escuelaing.ieti.tenistourcol.mapper;

import edu.escuelaing.ieti.tenistourcol.model.Player;
import edu.escuelaing.ieti.tenistourcol.repository.PlayerEntity;

public class PlayerMapper {
    private PlayerMapper() {
    }

    public static PlayerEntity map(final Player player) {

        //String name, mail, apodo, liga, ciudad, description;
        final PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setId(player.getId());
        playerEntity.setName(player.getName());
        playerEntity.setMail(player.getMail());
        playerEntity.setDescription(player.getDescription());
        playerEntity.setApodo(player.getApodo());
        playerEntity.setLiga(player.getLiga());
        playerEntity.setCiudad(player.getCiudad());
        playerEntity.setImagen(player.getImagen());
        return playerEntity;
    }

    public static Player map(final PlayerEntity playerEntity) {

        Player player = new Player();

        player.setId(playerEntity.getId());
        player.setName(playerEntity.getName());
        player.setDescription(playerEntity.getDescription());
        player.setMail(playerEntity.getMail());
        player.setApodo(playerEntity.getApodo());
        player.setLiga(playerEntity.getLiga());
        player.setImagen(playerEntity.getImagen());

        return player;
    }
}
