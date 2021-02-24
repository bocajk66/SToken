package io.samuelyoung.stoken.framework;

import io.samuelyoung.stoken.SToken;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class STFile {

    @Getter private File userFolder;

    private File configF;
    @Getter private YamlConfiguration config;

    public STFile(SToken sToken) {

        File data = sToken.getDataFolder();
        if(!data.exists()) data.mkdirs();

        userFolder = new File(data + File.separator + "users");
        if(!userFolder.exists()) userFolder.mkdirs();

        configF = new File(data, "Config.yml");
        if(!configF.exists()) sToken.saveResource("Config.yml", false);
        config = YamlConfiguration.loadConfiguration(configF);

    }

    public void save() {
        try { config.save(configF); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configF);
    }
}
