package znox.Oversee;

import org.bukkit.ChatColor;

public class Core {

    private static Menssages messages = new Menssages();
    static String name = "Server Name";
    static int maxPlayers = 8;

    public static Menssages getMenssages() { return messages; }

    public static class Menssages {
        private ChatColor color = ChatColor.GRAY;

        private String prefix() { return name; }

        public String alreadyCreatedSession() { return color + "Voce ja criou uma sessao."; }

        public String errorInEnter() { return color + "Ocorreu um erro ao entrar na sessao."; }

    }

}
