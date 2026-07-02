package com.academy.course.appcafe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicateOrderException extends AppCafeException {
    public DuplicateOrderException(String login) {
        super("Employee with login " + login +
                " cannot have more than 1 unbought order. Delete or buy unbought order", HttpStatus.BAD_GATEWAY);
        this.login = login;
    }

    private final String login;

}
