package io.samuelyoung.stoken;

import io.samuelyoung.stoken.command.STokenCommand;
import io.samuelyoung.stoken.dev.Metrics;
import io.samuelyoung.stoken.dev.TUtils;
import io.samuelyoung.stoken.event.STokenEvent;
import io.samuelyoung.stoken.framework.STFile;
import io.samuelyoung.stoken.framework.STokenPlaceholder;
import io.samuelyoung.stoken.modules.economy.TEconomy;
import io.samuelyoung.stoken.modules.player.TPlayer;
import io.samuelyoung.stoken.modules.player.TPlayerManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SToken extends JavaPlugin {

    //- API
    @Getter private static SToken inst;

    //- Framework
    @Getter private STFile stFile;

    //- Module
    @Getter private TPlayerManager tPlayerManager;
    @Getter private TEconomy tEconomy;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(TUtils.c("&6[SToken] Developed by Sykuu#5227 is now enabling"));

        //- API
        inst = this;

        //- Framework
        stFile = new STFile(this);

        //- Modules
        tPlayerManager = new TPlayerManager(this);
        tEconomy = new TEconomy(this);

        //- Events
        new STokenEvent(this);

        //- Command
        new STokenCommand(this);

        //- Placeholder
        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage(TUtils.c("&a[SToken] PlaceholderAPI plugin found, enabling placeholder: %SToken_balance%"));
            new STokenPlaceholder().register();
        }

        //- Metrics
        new Metrics(this, 10401);

        Bukkit.getConsoleSender().sendMessage(TUtils.c("&6[SToken] Developed by Sykuu#5227 is finished enabling"));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(TUtils.c("&6[SToken] Developed by Sykuu#5227 is now disabling"));

        //- Saving online player data
        tPlayerManager.getPlayers().forEach(TPlayer::save);

        //- API
        inst = null;

        Bukkit.getConsoleSender().sendMessage(TUtils.c("&6[SToken] Developed by Sykuu#5227 is finished disabling"));
    }
}
