package io.samuelyoung.stoken.command.sub.player;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.dev.TUtils;
import io.sy.basecommand.BaseCommand;
import io.sy.basecommand.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class STokenBalanceCommand extends BaseCommand {

    private SToken sToken;
    public STokenBalanceCommand(SToken sToken) {
        super(CommandInfo.builder()
            .plugin(sToken)
            .command("balance")
            .commandUsageMessage("/SToken")
            .consoleUse(false)
            .aliases(Arrays.asList("balance", "bal", "b"))
        .build());

        this.sToken = sToken;
    }

    @Override
    public void executePlayer(Player player, String [] args) {

        if(args.length == 1) {

            OfflinePlayer target;
            try {
                target = Bukkit.getOfflinePlayer(args[0]);
            } catch (Exception e) {
                player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.player-not-exists")
                    .replace("%player%", args[0])
                ));
                return;
            }

            if(!player.hasPermission(sToken.getStFile().getConfig().getString("permissions.balance-other"))) {
                player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.no-permission")));
                return;
            }

            player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.other-balance")
                .replace("%token_balance%", TUtils.formatNumber(sToken.getTEconomy().getBalance(target)))
                .replace("%player%", target.getName())
            ));

        } else {

            player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.self-balance")
                .replace("%token_balance%", TUtils.formatNumber(sToken.getTEconomy().getBalance(player)))
            ));

        }

    }
}
