package com.microservice.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path of the error", example = "uri=/api/update"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened", example = "500"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing the error happened", example = "Internal Server Error"
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when the error happened",
            example = "2024-11-09T10:12:51.7736411"
    )
    private LocalDateTime errorTime;
}
