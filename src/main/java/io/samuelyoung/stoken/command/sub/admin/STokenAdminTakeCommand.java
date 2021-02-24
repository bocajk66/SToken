package io.samuelyoung.stoken.command.sub.admin;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.dev.TUtils;
import io.sy.basecommand.BaseCommand;
import io.sy.basecommand.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;

public class STokenAdminTakeCommand extends BaseCommand {

    private SToken sToken;
    YamlConfiguration cfg;

    public STokenAdminTakeCommand(SToken sToken) {

        super(CommandInfo.builder()
            .plugin(sToken)
            .command("take")
            .commandUsageMessage("/SToken add [player] [amount]")
            .consoleUse(true)
            .aliases(Arrays.asList("take", "t"))
            .permission(sToken.getStFile().getConfig().getString("permissions.admin"))
        .build());

        this.sToken = sToken;
        cfg = sToken.getStFile().getConfig();

    }

    @Override
    public void executeBoth(CommandSender sender, String[] args) {

        if(args.length == 2) {

            //- Target
            OfflinePlayer target;
            try {
                target = Bukkit.getOfflinePlayer(args[0]);
            } catch (Exception e) {
                sender.sendMessage(TUtils.c(cfg.getString("messages.player-not-exists")
                        .replace("%player%", args[0])
                ));
                return;
            }

            //- Amount
            long amount;
            try {
                amount = Long.parseLong(args[1]);
                if(amount < 1) {
                    sender.sendMessage(TUtils.c(cfg.getString("messages.value-below-one")));
                    return;
                }
            } catch (Exception e) {
                sender.sendMessage(TUtils.c(cfg.getString("messages.invalid-number")));
                return;
            }

            if(sToken.getTEconomy().getBalance(target) < amount) {
                sender.sendMessage(TUtils.c(cfg.getString("messages.player-balance-too-low")
                    .replace("%player%", target.getName())
                    .replace("%token_amount%", TUtils.formatNumber(sToken.getTEconomy().getBalance(target)))
                ));
                return;
            }

            //- taking
            sToken.getTEconomy().takeBalance(target, amount);

            //- Messaging
            sender.sendMessage(TUtils.c(cfg.getString("messages.take-confirm-sender")
                    .replace("%token_amount%", TUtils.formatNumber(amount))
                    .replace("%player%", target.getName())
            ));

            if(target.isOnline()) {
                target.getPlayer().sendMessage(TUtils.c(cfg.getString("messages.take-confirm-receiver")
                        .replace("%token_amount%", TUtils.formatNumber(amount))
                ));
            }

        } else {
            sender.sendMessage(TUtils.c("&c[!] Wrong use of command. Use: " + getCommandUsageMessage()));
        }

    }

}
