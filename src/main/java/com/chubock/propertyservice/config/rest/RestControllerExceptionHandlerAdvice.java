package com.chubock.propertyservice.config.rest;

import com.chubock.propertyservice.component.Messages;
import com.chubock.propertyservice.exception.PropertyNotFoundException;
import com.chubock.propertyservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerExceptionHandlerAdvice {

    private static final String VALIDATION_ERROR_TITLE = "Validation Error";

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<Object> handlePropertyNotFoundException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        MessagesResponse response = new MessagesResponse();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.addMessage(new Message(VALIDATION_ERROR_TITLE, fieldName + ": " + errorMessage));
        });

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);

    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<MessagesResponse> handleRuntimeExceptions(RuntimeException ex) {
        MessagesResponse response = new MessagesResponse();
        response.addMessage(new Message(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    public static class Message {

        private String text;
        private String title = "";

        public Message() {
        }

        public Message(String text) {
            this.text = text;
        }

        public Message(String title, String text) {
            this.title = title;
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    public static class MessagesResponse {

        private List<Message> messages = new ArrayList<>();

        public List<Message> getMessages() {
            return messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
        }

        public void addMessage(Message message) {
            getMessages().add(message);
        }

    }

}
