import javax.websocket.Session;

public class ActivePlayer {
    public Session session = null;
    public String login = null;

    public ActivePlayer(Session session, String login) {
        this.session = session;
        this.login = login;
    }
}
