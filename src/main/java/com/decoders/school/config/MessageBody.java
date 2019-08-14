package com.decoders.school.config;

/**
 *
 * @author abdallah dabbas
 */
public class MessageBody {

    private static MessageBody messageBody = null;

    private String status;

    private String text;

    private Object body;

    private MessageBody() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static MessageBody getMessageBody() {
        return messageBody;
    }

    public static void setMessageBody(MessageBody messageBody) {
        MessageBody.messageBody = messageBody;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static MessageBody getInstance() {
        if (messageBody == null) {
            messageBody = new MessageBody();
        }

        messageBody.setBody(null);

        return messageBody;
    }
}
