package will;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import znox.Oversee.Oversee;
import znox.Type.Elements.Game;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        registerEvents(new Listener[] {
                new Oversee()
        });
    }

    @Game
    public void registerEvents(Listener[] listeners) {
        for (Method method : listeners.getClass().getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof Game) {
                    Game ann = (Game) annotation;
                    if (!ann.isStarted()) {
                        return;
                    }
                }
            }
        }

        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

}
