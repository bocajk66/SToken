package io.samuelyoung.stoken.event.drops;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.dev.TUtils;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class STokenMobDrop implements Listener {
    
    private SToken sToken;
    public STokenMobDrop(SToken sToken) { this.sToken = sToken; }

    @EventHandler
    public void entityDeath(EntityDeathEvent e) {

        if(e.getEntity().getKiller() != null) {

            Entity entity = e.getEntity();
            EntityType ent = entity.getType();
            Player player = e.getEntity().getKiller();

            if(entity instanceof Creature) {

                if(sToken.getStFile().getConfig().contains("give-tokens.mobs." + ent.toString())) {

                    //- Giving the tokens here.
                    String[] chance = sToken.getStFile().getConfig().getString("give-tokens.mobs." + ent.toString() + ".chance").split("/");
                    String[] amount = sToken.getStFile().getConfig().getString("give-tokens.mobs." + ent.toString() + ".amount").split(":");

                    int cMin = Integer.parseInt(chance[0]), cMax = Integer.parseInt(chance[1]);

                    if(new Random().nextInt(cMax) <= cMin) {

                        //- Getting amount
                        int aMin = Integer.parseInt(amount[0]), aMax = Integer.parseInt(amount[1]);
                        int give = new Random().nextInt(aMax) + aMin;

                        if(give < aMin) give = aMin;
                        if(give > aMax) give = aMax;

                        //- Giving
                        this.sToken.getTEconomy().addBalance(player, give);
                        player.sendMessage(TUtils.c(sToken.getStFile().getConfig().getString("messages.token-found")
                                .replace("%amount%", TUtils.formatNumber(give))
                        ));
                    }

                }

            }

        }

    }
    
}
