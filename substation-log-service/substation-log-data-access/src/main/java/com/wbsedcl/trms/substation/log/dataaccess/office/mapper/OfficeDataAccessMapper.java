package com.wbsedcl.trms.substation.log.dataaccess.office.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.domain.entity.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeDataAccessMapper {
    public OfficeEntity officeToOfficeEntity(Office office) {
        return new OfficeEntity(office.getId().getValue());
    }

    public Office officeEntityToOffice(OfficeEntity officeEntity) {
        return new Office(new OfficeId(officeEntity.getOfficeId()));
    }
}
