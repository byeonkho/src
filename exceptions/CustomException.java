package exceptions;

/**
 * CustomException is a subclass of {@link Exception}, intended for representing
 * application-specific errors throughout the system. It extends the standard exception
 * functionality with custom behaviors as needed.
 */
public class CustomException extends Exception {

    /**
     * Default constructor for CustomException. Creates a new instance of the exception without any message.
     */
    public CustomException() {
        super();
    }

    /**
     * Constructs a CustomException with a detailed message. This constructor is used to provide
     * an error message that explains the cause of the exception.
     *
     * @param message A string representing the detail message.
     */
    public CustomException(String message) {
        super(message);
    }
}
