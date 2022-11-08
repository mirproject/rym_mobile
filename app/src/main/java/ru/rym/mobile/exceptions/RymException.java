package ru.rym.mobile.exceptions;

/**
 * Обертка над RuntimeException. Включает в себя дополнительные возможности
 * форматирования текста сообщения.
 * @author skyhunter
 */
public class RymException extends RuntimeException {

    private static final long serialVersionUID = -4589861967052916443L;
    private String messageSystem = ""; //Сообщение для разработчиков

    public RymException(String message) {
	    super(message);
    }

    public RymException(String messageFormat, Object... messageArgs) {
	    super(String.format(messageFormat, messageArgs));
    }

    public RymException(Throwable throwable) {
	super(throwable);
    }

    public RymException(String message, String messageSystem) {
        super(message);
        this.messageSystem = messageSystem;
    }

    public String getMessageSystem() {
        return messageSystem;
    }
}
