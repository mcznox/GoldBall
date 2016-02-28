package znox.Oversee;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import znox.Type.Exception.SessionException;
import znox.Type.Elements.Game;
import znox.Type.Session;

import java.util.Map;

public class Oversee implements Listener {

    @EventHandler
    @Game
    public void joinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        e.setJoinMessage(null);

        Location location = null; // Definir localizacao

        player.teleport(location);
    }

    @EventHandler
    @Game(isStarted = true)
    public void severEvent(BlockBreakEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    @Game(isStarted = true)
    public void placeEvent(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    @Game(isStarted = true)
    public void foodEvent(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void createSession(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Session session = new Session();

        for (Map.Entry<Player[], Session> entry : session.getSessions().entrySet()) {
            for (Player players : entry.getKey()) {
                if (players == player) {
                    player.sendMessage(Core.getMenssages().alreadyCreatedSession());
                    return;
                }
            }
        }

        if (e.getAction().name().contains("RIGHT")) {
            if (block.getType() != Material.AIR) {
                if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                    Sign sign = (Sign) block;

                    if (sign.getLine(0).contains("Gold Ball")) {
                        if (sign.getLine(1).contains("#")) {
                            if (!sign.getLine(2).startsWith("0")) {
                                return;
                            }

                            session.setHost(player);
                            session.create();

                            sign.setLine(0, ChatColor.GOLD + "Gold Ball");
                            sign.setLine(1, "#" + ChatColor.DARK_RED + session.getId());
                            sign.setLine(2, ChatColor.GREEN.toString() + session.getPlayers().length + ChatColor.DARK_GREEN + "/" + Core.maxPlayers);
                            sign.setLine(3, ChatColor.GREEN + session.getState().toString());
                        }
                    }
                }
            }
         }
    }

    @EventHandler
    public void joinSession(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Session session = new Session();

        for (Map.Entry<Player[], Session> entry : session.getSessions().entrySet()) {
            for (Player players : entry.getKey()) {
                if (players == player) {
                    player.sendMessage(Core.getMenssages().alreadyCreatedSession());
                    return;
                }
            }
        }

        if (e.getAction().name().contains("RIGHT")) {
            if (block.getType() != Material.AIR) {
                if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                    Sign sign = (Sign) block;

                    if (sign.getLine(0).contains("Gold Ball")) {
                        if (sign.getLine(1).contains("#")) {
                            if (ChatColor.stripColor(sign.getLine(3)).equals(Session.SessionEnum.WAITING.toString())) {
                                String s = sign.getLine(1)
                                        .replace("#", "")
                                        .replace(" ", "");
                                try {
                                    int sessionInt = Integer.valueOf(s);
                                    if (session.getSession(sessionInt) != null) {
                                        // Transferir para jogo
                                    } else {
                                        player.sendMessage(Core.getMenssages().errorInEnter());
                                        throw new SessionException("joinSession (EventHandler)", "Session not found");
                                    }
                                } catch (NumberFormatException ex) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
