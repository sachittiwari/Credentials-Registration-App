package com.sachit.credentials.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrgMappingResponseDTO {

    private Long userId;
    private List<Long> organizationIds;
}
