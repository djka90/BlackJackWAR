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
import java.util.logging.*;
import java.lang.*;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {

    @Inject
    private DeviceSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {

        if (sessionHandler.counter == 0) {
            sessionHandler.myInit();
        }
        sessionHandler.counter++;

        sessionHandler.addSession(session);
        System.out.println("Opened session ID: " + session.getId());
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Closed session ID: " + session.getId());

        sessionHandler.checkClosedPlayerTable(session);
        sessionHandler.removeActivePlayer(session);
        for (ActivePlayer activePlayer: sessionHandler.activePlayers) {
            System.out.println(" " + activePlayer.login + " ");
        }
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(DeviceWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("sendName".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                sessionHandler.checkRegister(session, name);
            }

            if ("sendPassword".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                String instruction = jsonMessage.getString("instruction");
                sessionHandler.checkPasswordReg(name, instruction);
            }

            if ("sendName2".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                sessionHandler.checkLogin(session, name);
            }

            if ("sendPassword2".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                String instruction = jsonMessage.getString("instruction");
                sessionHandler.checkPasswordLog(session, name, instruction);
            }

            if ("table".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                String instruction = jsonMessage.getString("instruction");
                int tableNumber = Integer.parseInt(instruction);
                sessionHandler.checkTable(session,name,tableNumber);
            }

            if ("sendMessage".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                String instruction = jsonMessage.getString("instruction");
                String nameAndMessage = name + ": " + instruction;
                sessionHandler.checkMessage(name,nameAndMessage);
            }

            if ("addCard".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                sessionHandler.callForCard(session, name);
            }

            if ("pass".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                sessionHandler.callForResult(name);
            }

            if ("newGame".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                sessionHandler.callForNewGame(name);
            }

            if ("refreshRanking".equals(jsonMessage.getString("action"))) {

                String name = jsonMessage.getString("name");
                sessionHandler.callForNewRanking(session);
            }

        }
    }
}    