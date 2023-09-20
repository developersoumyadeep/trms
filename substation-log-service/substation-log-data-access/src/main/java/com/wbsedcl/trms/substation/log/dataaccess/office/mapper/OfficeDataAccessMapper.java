package com.wbsedcl.trms.substation.log.dataaccess.office.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.domain.entity.Office;
import org.springframework.stereotype.Component;

/*
    This class maps from domain com.wbsedcl.hr.management.domain.entity to data access layer com.wbsedcl.hr.management.domain.entity and vice-versa
 */
@Component
public class OfficeDataAccessMapper {
    public OfficeEntity officeToOfficeEntity(Office office) {
        return new OfficeEntity(office.getId().getValue(), office.getOfficeText());
    }

    public Office officeEntityToOffice(OfficeEntity officeEntity) {
        return new Office(new OfficeId(officeEntity.getOfficeId()));
    }
}
