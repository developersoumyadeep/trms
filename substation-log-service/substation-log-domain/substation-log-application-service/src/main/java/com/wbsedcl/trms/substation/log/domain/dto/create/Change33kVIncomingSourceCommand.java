package com.wbsedcl.trms.substation.log.domain.dto.create;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Change33kVIncomingSourceCommand {
    private String sourceChangeOverFromFeederId;
    private String sourceChangeOverToFeederId;
    private List<String> affectedPTRIds;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Change33kVSourceCommandContext context;
}
