import javax.inject.Inject;
import javax.json.*;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.*;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {

    //LinkedList<Table> tables = new LinkedList<>();
    //int counter = 0;

    @Inject
    private DeviceSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        /*if (counter == 0) {



            tables.add(new Table(1));
            tables.add(new Table(2));
            tables.add(new Table(3));
            tables.add(new Table(4));
            tables.add(new Table(5));

            tables.getLast().numberOfPlayers = 2;
        }
        counter++;*/
        sessionHandler.addSession(session);
        System.out.println("Opened session ID: " + session.getId());
    }

    @OnClose
    public void close(Session session) {

        sessionHandler.removeSession(session);
        System.out.println("Closed session ID: " + session.getId());
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("sendName2".equals(jsonMessage.getString("action"))) {

                LinkedList<String> logins = new LinkedList<>();

                logins.add("a");
                logins.add("abc");

                String name = jsonMessage.getString("name");

                boolean checker = false;
                for (String login: logins) {
                    if (login.equals(name)) checker = true;
                }
                if (checker == true) {
                    JsonObject mess = sessionHandler.createMessage("correctName",name);
                    sessionHandler.sendToSession(session,mess);
                }
                else {
                    JsonObject mess = sessionHandler.createMessage("wrongName",name);
                    sessionHandler.sendToSession(session,mess);
                }
            }

            if ("sendName2".equals(jsonMessage.getString("action"))) {

                    JsonObject mess = sessionHandler.createMessage("correctName","xxx");
                    sessionHandler.sendToSession(session,mess);
            }

            /*if ("table1".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");

                if (tables.get(0).numberOfPlayers < 2) {
                    if (tables.get(0).numberOfPlayers == 0) {
                        tables.get(0).name1 = name;
                        tables.get(0).session1 = session;
                        tables.get(0).numberOfPlayers = 1;
                    }
                    else {
                        tables.get(0).name2 = name;
                        tables.get(0).session2 = session;
                        tables.get(0).numberOfPlayers = 2;
                    }
                    JsonObject mess = sessionHandler.createMessage("correctTable","xxx");
                    sessionHandler.sendToSession(session,mess);
                }
                else {
                    JsonObject mess = sessionHandler.createMessage("wrongTable","xxx");
                    sessionHandler.sendToSession(session,mess);
                }
            }

            if ("table5".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");

                if (tables.get(4).numberOfPlayers < 2) {
                    if (tables.get(4).numberOfPlayers == 0) {
                        tables.get(4).name1 = name;
                        tables.get(4).session1 = session;
                        tables.get(4).numberOfPlayers = 1;
                    }
                    else {
                        tables.get(4).name2 = name;
                        tables.get(4).session2 = session;
                        tables.get(4).numberOfPlayers = 2;
                    }
                    JsonObject mess = sessionHandler.createMessage("correctTable","xxx");
                    sessionHandler.sendToSession(session,mess);
                }
                else {
                    JsonObject mess = sessionHandler.createMessage("wrongTable","xxx");
                    sessionHandler.sendToSession(session,mess);
                }
            }*/

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeDevice(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleDevice(id);
            }
        }
    }
}    