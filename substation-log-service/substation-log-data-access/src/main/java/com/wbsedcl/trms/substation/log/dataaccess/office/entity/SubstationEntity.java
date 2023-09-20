package com.wbsedcl.trms.substation.log.dataaccess.office.entity;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "ss_log_ss_m_view", schema = "substation_log_schema")
public class SubstationEntity extends OfficeEntity{
    private String substationContactNumber;

    @OneToMany
    @JoinTable( name ="substation_incoming_source_mapping",
            joinColumns = @JoinColumn(name = "substation_office_id", referencedColumnName = "office_id"),
            inverseJoinColumns = @JoinColumn(name = "incoming_source_feeder_id", referencedColumnName = "feeder_id"))
    private List<FeederEntity> incomingSourceFeeders;

    public SubstationEntity(String officeId, String officeText, String substationContactNumber, List<FeederEntity> incomingSourceFeeders) {
        super(officeId, officeText);
        this.substationContactNumber = substationContactNumber;
        this.incomingSourceFeeders = incomingSourceFeeders;
    }
}
