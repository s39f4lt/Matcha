package ru.school.matcha.handlers;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import ru.school.matcha.exceptions.JwtAuthenticationException;
import ru.school.matcha.security.jwt.JwtTokenProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@WebSocket
public class ChatWebSocketHandler {

    static Map<Session, String> sessionUsernameMap = new ConcurrentHashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        try {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
            String token = session.getUpgradeRequest().getParameterMap().get("x-auth-token").get(0);
            if (!jwtTokenProvider.validateToken(token)) {
                throw new JwtAuthenticationException("Credential are invalid");
            }
            String username = jwtTokenProvider.getUsernameFromToken(token);
            sessionUsernameMap.put(session, username);
            sendMessage(sessionUsernameMap.get(session), username + " joined the chat");
        } catch (JwtAuthenticationException ex) {
            log.error("Credentials are invalid");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendMessage(String sender, String message) {
        sessionUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(sender + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        String username = sessionUsernameMap.get(user);
        sessionUsernameMap.remove(user);
        sendMessage("Server", username + " left the chat");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        sendMessage(sessionUsernameMap.get(user), message);
    }

}
