package reyd.Utils;

import cn.nukkit.utils.Config;
import reyd.AntiBotMain;

import java.io.File;

public class AntiBotCFG {

    public AntiBotMain antiBotMain;
    public File file;
    public Config config;

    public AntiBotCFG(final AntiBotMain antiBotMain) {
        this.antiBotMain = antiBotMain;
        this.file = new File(antiBotMain.getDataFolder() + "/ConfigAntiBot.yml");
        this.config = new Config(this.file, 2);
    }

    public void createDefaultConfig() {
        this.addDefault("options.time", 15);
        this.addDefault("options.messages.success", "§aYou succesfully passed the captcha");
        this.addDefault("options.messages.failed", "§cYou failed the captcha");
        this.addDefault("options.messages.timeover", "§2Time is over");
        this.addDefault("options.messages.typecaptcha", "§7Type: §e");
    }

    public int Time() {
        return this.config.getInt("options.time");
    }

    public String Success() {
        return this.config.getString("options.messages.success");
    }

    public String Failed() {
        return this.config.getString("options.messages.failed");
    }

    public String TimeOver() {
        return this.config.getString("options.messages.timeover");
    }

    public String TypeCaptchaMSG() {
        return this.config.getString("options.messages.typecaptcha");
    }


    public void addDefault(final String path, final Object object) {
        if (!this.config.exists(path)) {
            this.config.set(path, object);
            this.config.save(this.file);
        }
    }

}
