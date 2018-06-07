package will.Type;

import org.bukkit.entity.Player;
import will.Type.Exception.SessionException;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * <h1>Session Class</h1>
 * <h2>Declare, create, add, remove and some others functions for Sessions</h2>
 *
 * @author Will
 * @version 1.0.3
 */

public class Session implements Cloneable {

    private Player host;
    private Player[] players;
    private SessionEnum state;
    private Hashtable<Player[], Session> sessions;
    private Hashtable<Integer, Session> sessionInt;
    private int id;

    public Session(Player player) {
        host = player;
        players = new Player[] { player };
        state = SessionEnum.NO;
    }

    public Session() {

    }

    public Player getHost() { return host; }

    public Player[] getPlayers() { return players; }

    public int getId() { return id; }

    public Player[] getPlayers(Hashtable<Player[], Session> sessions) {
        if (sessions != null) {
            for (Map.Entry entry : sessions.entrySet()) {

            }
        } else {
            throw new SessionException("getPlayers", "No session has been created");
        }

        return null;
    }

    public SessionEnum getState() { return state; }

    public Hashtable<Player[], Session> getSessions() { return sessions; }

    public Session getSession(int id) {
        for (Map.Entry<Integer, Session> entry : sessionInt.entrySet()) {
            if (entry.getKey() == id) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Session getSession() {
        if (sessions != null) {
            if (getPlayers() != null) {
                List<Player> list = null;
                for (Player player : getPlayers()) {
                    list.add(player);
                }

                if (list.contains(getHost())) {
                    return sessions.get(getPlayers());
                } else {
                    throw new SessionException("getSession", "No session linked with host player");
                }
            }
        } else {
            throw new SessionException("getSession", "No session has been created");
        }

        return this;
    }

    public Player setHost(Player player) {
        addPlayer(player);
        return host = player;
    }

    public Player[] setPlayers(Player[] players) { return this.players = players; }

    public SessionEnum setState(SessionEnum state) { return this.state = state; }

    public Session create() {
        for (Map.Entry<Player[], Session> entry : getSessions().entrySet()) {
            Integer i = null;
            for (Map.Entry<Integer, Session> id : sessionInt.entrySet()) {
                i = id.getKey();
            }
            if (entry.getValue() != this) {
                i ++;
                getSessions().put(getPlayers(), this);
                setState(SessionEnum.WAITING);
                sessionInt.put(i, this);
            } else {
                throw new SessionException("create", "Session already created");
            }
        }
        return this;
    }

    public Session addPlayer(Player player) {
        if (getState() != SessionEnum.NO) {
            for (Player players : getPlayers()) {
                if (players != player) {
                    List<Player> list = null;
                    list.add(players);
                    list.add(player);

                    setPlayers(list.toArray(new Player[0]));
                } else {
                    throw new SessionException("addPlayer", "Player already in session");
                }
            }
        } else {
            throw new SessionException("addPlayer", "Session was not started");
        }
        return this;
    }

    public Session removePlayer(Player player) {
        if (getSessions() != null) {
            for (Map.Entry<Player[], Session> entry : getSessions().entrySet()) {
                Player[] players = entry.getKey();
                List<Player> list = null;

                for (Player playerss : players) {
                    if (playerss != player) {
                        list.add(playerss);
                    }
                }

                setPlayers(list.toArray(new Player[0]));
            }
        } else {
            throw new SessionException("removePlayer", "No session has been created");
        }
        return this;
    }

    @Override
    public Session clone() {
        try {
            return (Session)this.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public enum SessionEnum {

        NO(0, "Fechada"),
        WAITING(1, "Aguardando"),
        STARTING(2, "Jogando");

        int id;
        String state;

        SessionEnum(int id, String state) {
            this.id = id;
            this.state = state;
        }

        public int getId() { return id; }

        @Override
        public String toString() {
            return state;
        }

    }

}
