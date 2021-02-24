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

public class STokenAdminAddCommand extends BaseCommand {

    private SToken sToken;
    YamlConfiguration cfg;

    public STokenAdminAddCommand(SToken sToken) {
        super(CommandInfo.builder()
            .plugin(sToken)
            .command("add")
            .commandUsageMessage("/SToken add [player] [amount]")
            .consoleUse(true)
            .aliases(Arrays.asList("add", "a"))
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

            //- Adding
            sToken.getTEconomy().addBalance(target, amount);

            //- Messaging
            sender.sendMessage(TUtils.c(cfg.getString("messages.add-confirm-sender")
                .replace("%token_amount%", TUtils.formatNumber(amount))
                .replace("%player%", target.getName())
            ));

            if(target.isOnline()) {
                target.getPlayer().sendMessage(TUtils.c(cfg.getString("messages.add-confirm-receiver")
                    .replace("%token_amount%", TUtils.formatNumber(amount))
                ));
            }

        } else {
            sender.sendMessage(TUtils.c("&c[!] Wrong use of command. Use: " + getCommandUsageMessage()));
        }

    }
}
