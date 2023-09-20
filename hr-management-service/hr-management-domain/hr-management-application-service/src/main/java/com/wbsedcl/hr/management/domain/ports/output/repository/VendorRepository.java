package com.wbsedcl.hr.management.domain.ports.output.repository;

import com.wbsedcl.hr.management.domain.entity.Vendor;

import java.util.Optional;

public interface VendorRepository {
    public Optional<Vendor> findVendorById(String vendorId);
}
