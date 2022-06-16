package reyd.Listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scheduler.TaskHandler;
import reyd.AntiBotMain;

import java.util.HashMap;
import java.util.Random;

public class PlayerJoinListener implements Listener {

    TaskHandler taskHandler;

    HashMap<Player, String> SaveCaptcha = new HashMap<>();

    @EventHandler
    public void SendCaptcha(PlayerJoinEvent event){


        taskHandler = Server.getInstance().getScheduler().scheduleRepeatingTask(new Task() {

            int count = AntiBotMain.getAntiBotCFG().Time();

            @Override
            public void onRun(int i) {

                if (count > 2){

                    count--;

                    if (SaveCaptcha.get(event.getPlayer()) == null){

                        SaveCaptcha.put(event.getPlayer(), generateRandomCaptcha(2));

                        event.getPlayer().sendTitle(AntiBotMain.getAntiBotCFG().TypeCaptchaMSG() + SaveCaptcha.get(event.getPlayer()));


                    } else {

                        event.getPlayer().sendTitle(AntiBotMain.getAntiBotCFG().TypeCaptchaMSG() + SaveCaptcha.get(event.getPlayer()));

                    }

                } else if (count > 1){
                    count--;

                    if (SaveCaptcha.get(event.getPlayer()) != null){

                        event.getPlayer().sendTitle(AntiBotMain.getAntiBotCFG().TimeOver());

                    }
                } else if (count > 0){

                    count--;
                    event.getPlayer().kick(AntiBotMain.getAntiBotCFG().TimeOver());
                    SaveCaptcha.remove(event.getPlayer());

                }

                else {

                    taskHandler.cancel();

                }

            }
        }, 20);

    }

    @EventHandler
    public void removeCaptchaIfQuit(PlayerQuitEvent event){

        if (SaveCaptcha.get(event.getPlayer()) != null){
            SaveCaptcha.remove(event.getPlayer());
        }

    }

    @EventHandler
    public void determineCaptcha(PlayerChatEvent event){
        if (SaveCaptcha.get(event.getPlayer()) != null){
            if (event.getMessage().equals(SaveCaptcha.get(event.getPlayer()))){

                event.getPlayer().sendTip(AntiBotMain.getAntiBotCFG().Success());
                SaveCaptcha.remove(event.getPlayer());
                taskHandler.cancel();
                event.setCancelled(true);

            } else {

                event.getPlayer().sendTip(AntiBotMain.getAntiBotCFG().Failed());
                event.setCancelled(true);

            }
        }
    }

    public static String generateRandomCaptcha(int len) {

        String chars = "QWERTYUIOPAqhjklzxcvbnmVBN1234567890SwertyuiopasdfgDFGHJKLZXCM";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();

    }

}
