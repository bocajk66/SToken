package io.samuelyoung.stoken.command;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.command.sub.admin.STokenAdminAddCommand;
import io.samuelyoung.stoken.command.sub.admin.STokenAdminSetCommand;
import io.samuelyoung.stoken.command.sub.admin.STokenAdminTakeCommand;
import io.samuelyoung.stoken.command.sub.player.STokenBalanceCommand;
import io.samuelyoung.stoken.dev.TUtils;
import io.sy.basecommand.BaseCommand;
import io.sy.basecommand.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class STokenCommand extends BaseCommand {

    private SToken sToken;
    public STokenCommand(SToken sToken) {
        super(CommandInfo.builder()
            .plugin(sToken)
            .command("SToken")
            .commandUsageMessage("/SToken")
            .consoleUse(true)
            .aliases(Arrays.asList("stoken", "stokens", "stok", "token", "tokens", "tok"))
        .build());

        this.sToken = sToken;

        //- Registering the command in the plugin
        PluginCommand command = sToken.getServer().getPluginCommand("SToken");
        assert command != null;
        command.setExecutor(this); command.setTabCompleter(this);


        //- Player Sub Commands
        subCommand(new STokenBalanceCommand(sToken));

        //- Admin Sub Command
        subCommand(new STokenAdminAddCommand(sToken));
        subCommand(new STokenAdminSetCommand(sToken));
        subCommand(new STokenAdminTakeCommand(sToken));
    }

    @Override
    public void executeBoth(CommandSender sender, String [] args) {

        if(args.length == 1) {

            OfflinePlayer target;
            try {
                target = Bukkit.getOfflinePlayer(args[0]);
            } catch (Exception e) {
                sender.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.player-not-exists")
                    .replace("%player%", args[0])
                ));
                return;
            }

            if(!sender.hasPermission(sToken.getStFile().getConfig().getString("permissions.balance-other"))) {
                sender.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.no-permission")));
                return;
            }

            sender.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.other-balance")
                .replace("%token_balance%", TUtils.formatNumber(sToken.getTEconomy().getBalance(target)))
                .replace("%player%", target.getName())
            ));

        } else {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.self-balance")
                        .replace("%token_balance%", TUtils.formatNumber(sToken.getTEconomy().getBalance(player)))
                ));
            } else {
                sender.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.player-only-command")));
            }
        }

    }
}
