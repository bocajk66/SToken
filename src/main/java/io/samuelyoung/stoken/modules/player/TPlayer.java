package io.samuelyoung.stoken.modules.player;

import io.samuelyoung.stoken.SToken;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class TPlayer {

    @Getter private Player player;

    @Getter private File file;
    @Getter private YamlConfiguration data;

    @Getter @Setter private long balance;

    //-
    public TPlayer(SToken sToken, Player player) {

        this.player = player;

        this.file = new File(sToken.getStFile().getUserFolder(), player.getUniqueId().toString() + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();

                data = YamlConfiguration.loadConfiguration(file);

                data.set("player-name", player.getName());
                data.set("player-uuid", player.getUniqueId().toString());
                data.set("balance", sToken.getStFile().getConfig().getLong("token-starting-balance"));

                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
        balance = data.getLong("balance");

    }

    public void save() {
        data.set("player-name", player.getName());
        data.set("balance", balance);

        try { data.save(file); }
        catch (IOException e) { e.printStackTrace(); }
    }
}