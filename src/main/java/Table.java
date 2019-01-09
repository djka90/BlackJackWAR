import javax.websocket.Session;

public class Table {
    public Session session1 = null;
    public String name1 = null;
    public Session session2 = null;
    public String name2 = null;
    public int numberOfPlayers = 0;
    public int tableNumber;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
