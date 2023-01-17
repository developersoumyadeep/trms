package com.wbsedcl.trms.substation.log.domain.validation;

import com.wbsedcl.trms.domain.valueobject.AssetId;
import com.wbsedcl.trms.substation.log.domain.entity.Asset;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionDomainException;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.AssetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
@Slf4j
public class AssetIdValidator implements ConstraintValidator<ValidAssetId, String> {

    private final AssetRepository assetRepository;

    public AssetIdValidator(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public void initialize(ValidAssetId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String assetIdToValidate, ConstraintValidatorContext context) {
        if (assetIdToValidate == null || assetIdToValidate.trim().equals("")) {
            return false;
        }
        Optional<Asset> asset = assetRepository.findAsset(assetIdToValidate);
        if(asset.isEmpty()) {
            log.error("Asset with id {} does not exist", assetIdToValidate);
            return false;
        }
        return true;
    }
}
