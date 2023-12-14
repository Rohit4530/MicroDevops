package com.rohit.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Response"
)
public class ResponseDto {
    private String statusCode;
    private  String statusMessage;
}
