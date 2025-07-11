package org.koreait.global.advices;

import lombok.RequiredArgsConstructor;
import org.koreait.admin.rests.JSONError;
import org.koreait.global.exceptions.CommonException;
import org.koreait.global.libs.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestControllerAdvice.class)
public class CommonRestControllerAdvice {
    private final Utils utils;
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONError> errorHandler(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Object message = e.getMessage();

        if (e instanceof CommonException commonException){
            status = commonException.getStatus();
            Map<String, List<String>> errorMessages = commonException.getErrorMessages();

            if (errorMessages != null) {
                message = errorMessages;
            } else {
                if (commonException.isErrorCode()) {
                    message = utils.getMessage((String)message);
                }
            }
        }
        e.printStackTrace();
        return ResponseEntity.status(status).body(new JSONError(status, message));
    }
}
