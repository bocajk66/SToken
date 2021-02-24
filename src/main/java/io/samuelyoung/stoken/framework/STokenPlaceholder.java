package io.samuelyoung.stoken.framework;

import io.samuelyoung.stoken.SToken;
import io.samuelyoung.stoken.dev.TUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class STokenPlaceholder extends PlaceholderExpansion {

    private SToken plugin;

    public boolean canRegister(){
        plugin = SToken.getInst();
        return plugin != null;
    }

    @Override
    public String getAuthor(){
        return "Sykuu#5227";
    }

    @Override
    public String getIdentifier(){
        return "SToken";
    }

    @Override
    public String getRequiredPlugin(){
        return null;
    }

    @Override
    public String getVersion(){
        return SToken.getInst() != null ? SToken.getInst().getDescription().getVersion() : "0.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null) return "";

        //- %SToken_balance%
        if(identifier.equals("balance")){
            return TUtils.formatNumber(SToken.getInst().getTEconomy().getBalance(player));
        }

        return "";
    }

}
