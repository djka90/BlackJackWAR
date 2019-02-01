import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.HashSet;
import java.util.*;
import java.util.logging.*;
import javax.json.*;
import javax.json.spi.*;
import javax.websocket.Session;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class DeviceSessionHandler {
    //private int deviceId = 0;
    private final Set<Session> sessions = new HashSet<>();
    //private final Set<Device> devices = new HashSet<>();
    LinkedList<Player> players = new LinkedList<>();
    LinkedList<Table> tables = new LinkedList<>();
    LinkedList<ActivePlayer> activePlayers = new LinkedList<>();
    int counter = 0;
    int userId = 0;
    Connection connection = ConnectionConfiguration.getConnection();;

    public void myInit() {
        //Connection connection = null;
        //players.add(new Player("a"));
        //players.add(new Player("abc"));

        tables.add(new Table(1));
        tables.add(new Table(2));
        tables.add(new Table(3));
        tables.add(new Table(4));
        tables.add(new Table(5));

        try
        {
            //connection = ConnectionConfiguration.getConnection();
            if (connection != null) {
                System.out.println("Connection with DB established");
                //insertTest(connection);
                selectPlayers(connection);
                //Statement statement = connection.createStatement();
                //statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES (8, 'ddd', 'eee', 30)");
            }
        }catch(Exception e){
            e.printStackTrace();
        }/*finally{
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Connection DB Error!");
                }
            }
        }*/

        //tables.getLast().numberOfPlayers = 2;
    }





    public boolean selectTest(Connection connection){
        try{
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM SYSTEM.TEST_TABLE";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                Integer id = resultSet.getInt("ID");
                String name = resultSet.getString("TEST");

                System.out.format("%s, %s\n", id, name);
                //statement.close();
            }

            System.out.println("Data selected");
        }catch(SQLException e){
            System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return  true;
    }

    public boolean selectPlayers(Connection connection){
        try{
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM SYSTEM.BJ_USERS ORDER BY SYSTEM.BJ_USERS.RANKING DESC";
            //String query = "SELECT * FROM SYSTEM.BJ_USERS";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                players.add(new Player(resultSet.getString("LOGIN"),resultSet.getString("PASSWORD"),resultSet.getInt("RANKING")));
                userId++;
            }

            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public LinkedList<Player> selectPlayerGames(Connection connection, String playerName){
        LinkedList<Player> games = new LinkedList<>();
        try{
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM SYSTEM.BJ_MY_GAMES WHERE SYSTEM.BJ_MY_GAMES.PLAYER = '"+playerName+"'";
            //String query = "SELECT * FROM SYSTEM.BJ_USERS";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                games.add(new Player(resultSet.getString("OPONENT"),resultSet.getString("RESULT"),0));
            }

            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return games;
        }
        return games;
    }

    public boolean insertPlayer(Connection connection, Player player){
        try{
            Statement statement = connection.createStatement();
            userId++;
            statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES ("+userId+", '"+player.login+"', '"+player.password+"', "+player.rank+")");
            //statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES (7, "+player.login+", "+player.password+", "+player.rank+")");
            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertGame(Connection connection, String playerName, String opponentName, String gameResult){
        try{
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO SYSTEM.BJ_MY_GAMES VALUES (1, '"+playerName+"', '"+opponentName+"', '"+gameResult+"')");
            //statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES (7, "+player.login+", "+player.password+", "+player.rank+")");
            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updatePlayerPass(Connection connection, Player player){
        try{
            Statement statement = connection.createStatement();
            statement.execute("UPDATE SYSTEM.BJ_USERS SET PASSWORD = '"+player.password+"' where LOGIN = '"+player.login+"'");
            //statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES (7, "+player.login+", "+player.password+", "+player.rank+")");
            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return  true;
    }

    public boolean updatePlayerRank(Connection connection, Player player){
        try{
            Statement statement = connection.createStatement();
            statement.execute("UPDATE SYSTEM.BJ_USERS SET RANKING = '"+player.rank+"' where LOGIN = '"+player.login+"'");
            //statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES (7, "+player.login+", "+player.password+", "+player.rank+")");
            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return  true;
    }

    public boolean insertSession(Session session){
        try{
            Statement statement = connection.createStatement();
            //statement.execute("INSERT INTO SYSTEM.BJ_SESSIONS VALUES (1, '"+session.getId()+"', '"+session.isOpen()+"', SYSDATE)");
            String sessionName = session.getId();
            Character sessionOpen;
            if (session.isOpen()){
                sessionOpen = 'O';
            } else sessionOpen = 'C';
            statement.execute("INSERT INTO SYSTEM.BJ_SESSIONS VALUES (1, '"+sessionName+"', '"+sessionOpen+"', SYSDATE)");
            //statement.execute("INSERT INTO SYSTEM.BJ_USERS VALUES (7, "+player.login+", "+player.password+", "+player.rank+")");
            //System.out.println("Data selected");
        }catch(SQLException e){
            //System.out.println("Select Error");
            e.printStackTrace();
            return false;
        }
        return  true;
    }







    public void checkRegister(Session session, String name) {

        boolean checker = true;
        for (Player player: players) {
            if (player.login.equals(name)) checker = false;
        }
        if (checker == true) {
            Player newPlayer = new Player(name);
            players.add(newPlayer);
            activePlayers.add(new ActivePlayer(session,name));
            JsonObject mess = createMessage("correctName", name, "xxx");

            insertPlayer(connection,newPlayer);

            sendToSession(session,mess);
        }
        else {
            JsonObject mess = createMessage("wrongName", name, "xxx");
            sendToSession(session,mess);
        }
    }

    public void checkPasswordReg(Session session, String name, String instruction) {

        for (Player player : players) {
            if (player.login.equals(name)) {
                player.password = instruction;
                updatePlayerPass(connection, player);
            }
        }

        String emptyTables = "";
        String halfTables = "";
        for (Table table : tables) {
            if (table.numberOfPlayers == 2);
            else if (table.numberOfPlayers == 1) halfTables = halfTables + table.tableNumber + " ";
            else {
                emptyTables = emptyTables + table.tableNumber + " ";
            }
        }
        if (emptyTables.equals("")) ;
        else {
            emptyTables = emptyTables.substring(0,emptyTables.length()-1);
        }
        if (halfTables.equals("")) ;
        else {
            halfTables = halfTables.substring(0,halfTables.length()-1);
        }

        JsonObject mess = createMessage("correctPassword", emptyTables, halfTables);
        sendToSession(session,mess);
    }

    public void checkLogin(Session session, String name) {

        boolean checker = false;
        for (Player player: players) {
            if (player.login.equals(name)) checker = true;
        }
        if (checker == true) {
            for (ActivePlayer activePlayer: activePlayers) {
                if (activePlayer.login.equals(name)) checker = false;
            }
        }
        if (checker == true) {
            activePlayers.add(new ActivePlayer(session,name));
            JsonObject mess = createMessage("correctName2", name, "xxx");
            sendToSession(session,mess);
        }
        else {
            JsonObject mess = createMessage("wrongName2", name, "xxx");
            sendToSession(session,mess);
        }
    }

    public void checkPasswordLog(Session session, String name, String instruction) {

        boolean checker = false;
        for (Player player: players) {
            if (player.login.equals(name) && player.password.equals(instruction)) checker = true;
        }
        if (checker == true) {

            String emptyTables = "";
            String halfTables = "";
            for (Table table : tables) {
                if (table.numberOfPlayers == 2);
                else if (table.numberOfPlayers == 1) halfTables = halfTables + table.tableNumber + " ";
                else {
                    emptyTables = emptyTables + table.tableNumber + " ";
                }
            }
            if (emptyTables.equals("")) ;
            else {
                emptyTables = emptyTables.substring(0,emptyTables.length()-1);
            }
            if (halfTables.equals("")) ;
            else {
                halfTables = halfTables.substring(0,halfTables.length()-1);
            }

            JsonObject mess = createMessage("correctPassword2", emptyTables, halfTables);
            sendToSession(session,mess);
        }
        else {
            JsonObject mess = createMessage("wrongPassword2", "xxx", "xxx");
            sendToSession(session,mess);
        }
    }

    public void checkTable(Session session, String name, int tableNumber) {

        int indexTable = tableNumber - 1;
        Table table = tables.get(indexTable);
        if (table.numberOfPlayers < 2) {
            if (table.numberOfPlayers == 0) {
                table.name1 = name;
                table.session1 = session;
                table.numberOfPlayers = 1;
            }
            else {
                table.name2 = name;
                table.session2 = session;
                table.numberOfPlayers = 2;
            }
            JsonObject mess = createMessage("correctTable","xxx", "xxx");
            sendToSession(session,mess);
        }
        else {
            JsonObject mess = createMessage("wrongTable","xxx", "xxx");
            sendToSession(session,mess);
        }
    }

    public void checkMessage(String name, String textOfMessage) {

        JsonObject mess = createMessage("newMessage", name, textOfMessage);
        for (Table table : tables) {
            if (name.equals(table.name1)||name.equals(table.name2)) {
                sendToSession(table.session1,mess);
                sendToSession(table.session2,mess);
            }
        }
    }

    public void callForCard(Session session, String name) {
        Card card = null;
        String cardName = null;
        String cardId = null;
        for (Table table : tables) {
            if (name.equals(table.name1)||name.equals(table.name2)) {
                card = table.randomCard(name);
                cardName = "{" + card.figure + card.color + "}";
                cardId = ""+card.id;
            }
        }
        JsonObject mess = createMessage("newCard", cardId, cardName);
        sendToSession(session,mess);
    }

    public Table findTable (String name) {
        Table thisTable = null;
        for (Table table : tables) {
            if (name.equals(table.name1)||name.equals(table.name2)) {
                thisTable = table;
            }
        }
        return thisTable;
    }

    public Table findTableBySession (Session session) {
        Table thisTable = null;
        for (Table table : tables) {
            if (session.equals(table.session1)||session.equals(table.session2)) {
                thisTable = table;
            }
        }
        return thisTable;
    }

    public void callForResult(String name) {
        int result = -1;
        Table thisTable = this.findTable(name);
        if (name.equals(thisTable.name1)) thisTable.pass1 = 1;
        else thisTable.pass2 = 1;
        result = thisTable.resultOfGame();
        if (result == -1) ;
        else if (result == 0) {
            JsonObject mess = createMessage("gameResult", "xxx", "DRAW!!! "+thisTable.result1+" vs. "+thisTable.result2);
            sendToSession(thisTable.session1,mess);
            sendToSession(thisTable.session2,mess);
            insertGame(connection,thisTable.name1,thisTable.name2,"DRAW");
            insertGame(connection,thisTable.name2,thisTable.name1,"DRAW");
        } else if (result == 1) {
            JsonObject mess1 = createMessage("gameResult", "xxx", "You WIN!!! "+thisTable.result1+" vs. "+thisTable.result2);
            sendToSession(thisTable.session1,mess1);
            insertGame(connection,thisTable.name1,thisTable.name2,"WIN");
            JsonObject mess2 = createMessage("gameResult", "xxx", "You LOSE!!! "+thisTable.result2+" vs. "+thisTable.result1);
            sendToSession(thisTable.session2,mess2);
            insertGame(connection,thisTable.name2,thisTable.name1,"LOSE");
            for (Player player : players) {
                if (player.login.equals(thisTable.name1)) {
                    player.rank = player.rank + 1;
                    updatePlayerRank(connection,player);
                }
                else if (player.login.equals(thisTable.name2)) {
                    player.rank = player.rank - 1;
                    updatePlayerRank(connection,player);
                }
            }
        } else if (result == 2) {
            JsonObject mess3 = createMessage("gameResult", "xxx", "You LOSE!!! "+thisTable.result1+" vs. "+thisTable.result2);
            sendToSession(thisTable.session1,mess3);
            insertGame(connection,thisTable.name1,thisTable.name2,"LOSE");
            JsonObject mess4 = createMessage("gameResult", "xxx", "You WIN!!! "+thisTable.result2+" vs. "+thisTable.result1);
            sendToSession(thisTable.session2,mess4);
            insertGame(connection,thisTable.name2,thisTable.name1,"WIN");
            for (Player player : players) {
                if (player.login.equals(thisTable.name1)) {
                    player.rank = player.rank - 1;
                    updatePlayerRank(connection,player);
                }
                else if (player.login.equals(thisTable.name2)) {
                    player.rank = player.rank + 1;
                    updatePlayerRank(connection,player);
                }
            }
        }
    }

    public void callForNewGame(String name) {
        Table thisTable = this.findTable(name);
        if (name.equals(thisTable.name1)) thisTable.newGame1 = 1;
        else if (name.equals(thisTable.name2)) thisTable.newGame2 = 1;
        if (thisTable.newGame1 == 0 || thisTable.newGame2 == 0) ;
        else {
            thisTable.resetGame();
            JsonObject mess = createMessage("newGameStart", "xxx", "xxx");
            sendToSession(thisTable.session1,mess);
            sendToSession(thisTable.session2,mess);
        }
    }

    public void sortRanking() {
    }

    public String generateRanking() {
        String ranking = "";
        int lp = 1;
        for (Player player: players) {
            ranking = ranking + lp + ". " + player.login + " " + player.rank + "<br>";
            lp++;
        }
        return ranking;
    }

    public void callForNewRanking(Session session) {
        this.sortRanking();
        String ranking = this.generateRanking();
        JsonObject mess = createMessage("rankingRefreshed", "xxx", ranking);
        sendToSession(session,mess);
    }

    public void callForNewRanking2(Session session) {
        String []ranking = new String[3];
        int lp = 1;
        for (Player player: players) {
            ranking[0] = lp+".";
            ranking[1] = player.login;
            ranking[2] = player.rank+"";
            JsonObject mess = createMessageRow("newRankingRow", ranking[0], ranking[1], ranking[2]);
            sendToSession(session,mess);
            lp++;
        }
        JsonObject mess = createMessage("rankingRefreshed", "xxx", "xxx");
        sendToSession(session,mess);
    }

    public void callForNewMyGames(Session session, String playerName) {
        LinkedList<Player> games = selectPlayerGames(connection, playerName);
        String []myGames = new String[3];
        int lp = 1;
        for (Player game: games) {
            myGames[0] = lp+".";
            myGames[1] = game.login;
            myGames[2] = game.password;
            JsonObject mess = createMessageRow("newMyGamesRow", myGames[0], myGames[1], myGames[2]);
            sendToSession(session,mess);
            lp++;
        }
        JsonObject mess = createMessage("myGamesDelivered", "xxx", "xxx");
        sendToSession(session,mess);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void checkClosedPlayerTable(Session session) {

        Table thisTable = this.findTableBySession(session);
        if (thisTable == null) ;
        else {
            if (thisTable.session2 == null) ;
            else {

                thisTable.numberOfPlayers = 0;

                String emptyTables = "";
                String halfTables = "";
                for (Table table : tables) {
                    if (table.numberOfPlayers == 2);
                    else if (table.numberOfPlayers == 1) halfTables = halfTables + table.tableNumber + " ";
                    else {
                        emptyTables = emptyTables + table.tableNumber + " ";
                    }
                }
                if (emptyTables.equals("")) ;
                else {
                    emptyTables = emptyTables.substring(0,emptyTables.length()-1);
                }
                if (halfTables.equals("")) ;
                else {
                    halfTables = halfTables.substring(0,halfTables.length()-1);
                }

                JsonObject mess = createMessage("opponentLost", emptyTables, halfTables);
                if (session.equals(thisTable.session1)) sendToSession(thisTable.session2,mess);
                else if (session.equals(thisTable.session2)) sendToSession(thisTable.session1,mess);
            }
            thisTable.resetTable();
        }
    }

    public void removeActivePlayer(Session session) {
        Iterator<ActivePlayer> it = activePlayers.iterator();
        while (it.hasNext()) {
            ActivePlayer activePlayer = it.next();
            if (activePlayer.session.equals(session)) {
                it.remove();
            }
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public JsonObject createMessage(String action, String name, String instruction) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", action)
                .add("name", name)
                .add("instruction", instruction)
                .build();
        return addMessage;
    }

    public JsonObject createMessageRow(String action, String col1, String col2, String col3) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", action)
                .add("col1", col1)
                .add("col2", col2)
                .add("col3", col3)
                .build();
        return addMessage;
    }

    public void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    public void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}