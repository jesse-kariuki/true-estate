package com.trustestate.backend.profile.service;

import com.trustestate.backend.profile.dto.LandlordDto;
import com.trustestate.backend.profile.dto.TenantDto;
import com.trustestate.backend.profile.models.Landlord;
import com.trustestate.backend.profile.models.Tenant;

public interface ProfileService {

    Tenant createTenantProfile(String userId, TenantDto tenantDto);
    Landlord createLandlordProfile(String userId, LandlordDto landlord);

}
