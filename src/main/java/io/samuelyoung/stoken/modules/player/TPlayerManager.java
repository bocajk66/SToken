package io.samuelyoung.stoken.modules.player;

import io.samuelyoung.stoken.SToken;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class TPlayerManager {

    private SToken sToken;
    @Getter private HashSet<TPlayer> players;

    public TPlayerManager(SToken sToken) {
        this.sToken = sToken;

        this.players = new HashSet<>();

        //- Loading data for any online players
        for(Player player : sToken.getServer().getOnlinePlayers()) {
            players.add(new TPlayer(sToken, player));
        }

    }

    //- Methods
    public TPlayer create(Player player) {
        TPlayer tPlayer = new TPlayer(sToken, player);
        players.add(tPlayer);
        return tPlayer;
    }

    public TPlayer get(Player player) {
        for(TPlayer tPlayer : players) {
            if(tPlayer.getPlayer().getUniqueId().toString().matches(player.getUniqueId().toString())) return tPlayer;
        }
        return null;
    }

    public void remove(Player player) {
        TPlayer tPlayer = get(player);

        if(tPlayer != null) {
            tPlayer.save();
            players.remove(tPlayer);
        }
    }
}
