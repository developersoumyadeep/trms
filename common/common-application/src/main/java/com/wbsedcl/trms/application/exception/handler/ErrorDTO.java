package com.wbsedcl.trms.application.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorDTO {
    private List<String> message;
    private int statusCode;
    private long timestamp;
}
