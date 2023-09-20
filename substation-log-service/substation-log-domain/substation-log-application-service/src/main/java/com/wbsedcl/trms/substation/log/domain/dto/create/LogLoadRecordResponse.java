package com.wbsedcl.trms.substation.log.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LogLoadRecordResponse {
    private String message;
    private UUID loadRecordId;
}
