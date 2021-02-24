package io.samuelyoung.stoken.event.pl;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.modules.player.TPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SPlayerJoinQuit implements Listener {

    private SToken sToken;
    public SPlayerJoinQuit(SToken sToken) { this.sToken = sToken; }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        TPlayer tPlayer = sToken.getTPlayerManager().create(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        sToken.getTPlayerManager().remove(player);
    }

}
