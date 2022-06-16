package reyd;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import reyd.Listener.PlayerJoinListener;
import reyd.Utils.AntiBotCFG;

public class AntiBotMain extends PluginBase {

    public static AntiBotMain instance;

    private static AntiBotCFG antiBotCFG;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        antiBotCFG = new AntiBotCFG(this);
        antiBotCFG.createDefaultConfig();

        registerEvents();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerEvents(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }

    public static AntiBotCFG getAntiBotCFG(){
        return antiBotCFG;
    }

}
