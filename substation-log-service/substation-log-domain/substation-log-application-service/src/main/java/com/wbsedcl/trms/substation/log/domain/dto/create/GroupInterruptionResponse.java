package com.wbsedcl.trms.substation.log.domain.dto.create;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class GroupInterruptionResponse {
    private String message;
    private List<LogInterruptionResponse> interruptionResponses;
}
