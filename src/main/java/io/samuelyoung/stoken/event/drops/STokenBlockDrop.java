package io.samuelyoung.stoken.event.drops;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.dev.TUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class STokenBlockDrop implements Listener {
    
    private SToken sToken;
    public STokenBlockDrop(SToken sToken) { this.sToken = sToken; }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Player player = e.getPlayer();
        Block block = e.getBlock();

        if (sToken.getStFile().getConfig().contains("give-tokens.blocks." + block.getType().toString())) {

            String[] chance = sToken.getStFile().getConfig().getString("give-tokens.blocks." + block.getType().toString() + ".chance").split("/");
            String[] amount = sToken.getStFile().getConfig().getString("give-tokens.blocks." + block.getType().toString() + ".amount").split(":");
            int cMin = Integer.parseInt(chance[0]), cMax = Integer.parseInt(chance[1]);

            if ((new Random()).nextInt(cMax) <= cMin) {

                int aMin = Integer.parseInt(amount[0]), aMax = Integer.parseInt(amount[1]);
                int give = (new Random()).nextInt(aMax) + aMin;

                give = (give < aMin) ? aMin : (Math.min(give, aMax));

                sToken.getTEconomy().addBalance(player, give);
                player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.token-found")
                        .replace("%amount%", TUtils.formatNumber(give))));

            }

        }
    }
}
