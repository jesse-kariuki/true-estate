package com.trustestate.backend.property.service;

import com.trustestate.backend.common.VerificationStatus;
import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.profile.models.Landlord;
import com.trustestate.backend.profile.repository.LandlordRepository;
import com.trustestate.backend.property.dto.PropertyRegistrationRequest;
import com.trustestate.backend.property.models.Property;
import com.trustestate.backend.property.models.Unit;
import com.trustestate.backend.property.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final LandlordRepository landlordRepository;

    @Transactional
    public Property registerProperty(PropertyRegistrationRequest request) {
        String currentUserEmail = SecurityContextHolder.getContext()
                .getAuthentication().getName();


        Landlord landlord = landlordRepository.findByUserEmail(currentUserEmail)
                .orElseThrow(() -> new UserException("Authenticated Landlord profile not found"));


        Property property = new Property();
        property.setLandlord(landlord);
        property.setName(request.getName());
        property.setLrNumber(request.getLrNumber());
        property.setLocation(request.getLocation());
        property.setSectionalConverted(request.isSectionalConverted());

        List<Unit> units = request.getUnits().stream().map(uReq -> {
            Unit unit = new Unit();
            unit.setUnitNumber(uReq.getUnitNumber());
            unit.setSectionalTitleNo(uReq.getSectionalTitleNo());
            unit.setRentAmount(uReq.getRentAmount());
            unit.setProperty(property);
            unit.setVerificationStatus(VerificationStatus.PENDING);
            return unit;
        }).collect(Collectors.toList());

        property.setUnits(units);

        return propertyRepository.save(property);

    }


}
