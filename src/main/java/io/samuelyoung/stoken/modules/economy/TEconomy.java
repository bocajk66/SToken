package io.samuelyoung.stoken.modules.economy;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.modules.player.TPlayer;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TEconomy {

    private SToken sToken;
    public TEconomy(SToken sToken) { this.sToken = sToken; }

    //- Methods: setBalance, hasBalance, addBalance, takeBalance
    public boolean hasBalance(OfflinePlayer player, long amount) {
        if (player.isOnline()) {

            TPlayer tPlayer = sToken.getTPlayerManager().get(player.getPlayer());
            return tPlayer.getBalance() >= amount;

        } else {

            File file = new File(sToken.getStFile().getUserFolder(), player.getUniqueId().toString() + ".yml");
            if(file.exists()) {

                YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
                return data.getLong("balance") >= amount;

            } else return false;

        }
    }

    public long getBalance(OfflinePlayer player) {
        if (player.isOnline()) {

            TPlayer tPlayer = sToken.getTPlayerManager().get(player.getPlayer());
            return tPlayer.getBalance();

        } else {

            File file = new File(sToken.getStFile().getUserFolder(), player.getUniqueId().toString() + ".yml");
            if(file.exists()) {

                YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
                return data.getLong("balance");

            } else return -1;

        }
    }

    public long setBalance(OfflinePlayer player, long amount) {
        if (player.isOnline()) {

            TPlayer tPlayer = sToken.getTPlayerManager().get(player.getPlayer());
            tPlayer.setBalance(amount);

            return amount;

        } else {

            File file = new File(sToken.getStFile().getUserFolder(), player.getUniqueId().toString() + ".yml");
            if(file.exists()) {

                YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
                data.set("balance", amount);
                try {
                    data.save(file);
                    return amount;
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }

            } else return -1;

        }
    }

    public long takeBalance(OfflinePlayer player, long amount) {
        if (player.isOnline()) {

            TPlayer tPlayer = sToken.getTPlayerManager().get(player.getPlayer());
            tPlayer.setBalance(Math.max(tPlayer.getBalance() - amount, 0));

            return tPlayer.getBalance();

        } else {

            File file = new File(sToken.getStFile().getUserFolder(), player.getUniqueId().toString() + ".yml");
            if(file.exists()) {

                YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
                data.set("balance", Math.max(data.getLong("balance") - amount, 0));

                try {
                    data.save(file);
                    return data.getLong("balance");
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }

            } else return -1;

        }
    }

    public long addBalance(OfflinePlayer player, long amount) {
        if (player.isOnline()) {

            TPlayer tPlayer = sToken.getTPlayerManager().get(player.getPlayer());
            tPlayer.setBalance(tPlayer.getBalance() + amount);

            return tPlayer.getBalance();

        } else {

            File file = new File(sToken.getStFile().getUserFolder(), player.getUniqueId().toString() + ".yml");
            if(file.exists()) {

                YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
                data.set("balance", data.getLong("balance") + amount);

                try {
                    data.save(file);
                    return data.getLong("balance");
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }

            } else return -1;

        }
    }

}
