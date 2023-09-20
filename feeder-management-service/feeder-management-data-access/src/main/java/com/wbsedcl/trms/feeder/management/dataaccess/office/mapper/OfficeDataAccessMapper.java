package com.wbsedcl.trms.feeder.management.dataaccess.office.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.feeder.management.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.feeder.management.domain.entity.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeDataAccessMapper {
    public OfficeEntity officeToOfficeEntity(Office office) {
        return OfficeEntity.builder()
                .officeId(office.getId().getValue())
                .officeText(office.getOfficeText())
                .build();
    }

    public Office officeEntityToOffice(OfficeEntity officeEntity) {
        return Office.newBuilder()
                .officeId(new OfficeId(officeEntity.getOfficeId()))
                .officeText(officeEntity.getOfficeText())
                .build();
    }
}
