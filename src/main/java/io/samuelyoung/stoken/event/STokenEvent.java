package io.samuelyoung.stoken.event;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.event.drops.STokenBlockDrop;
import io.samuelyoung.stoken.event.drops.STokenMobDrop;
import io.samuelyoung.stoken.event.pl.SPlayerJoinQuit;
import org.bukkit.plugin.PluginManager;

public class STokenEvent {
    public STokenEvent(SToken sToken) {
        PluginManager pm = sToken.getServer().getPluginManager();

        pm.registerEvents(new SPlayerJoinQuit(sToken), sToken);

        pm.registerEvents(new STokenBlockDrop(sToken), sToken);
        pm.registerEvents(new STokenMobDrop(sToken), sToken);
    }
}
