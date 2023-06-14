package Notifications;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(MessageRequest message) throws Exception {
        // Process the message and return a response
        return new MessageResponse(message.getSender(), message.getContent());
    }

}
