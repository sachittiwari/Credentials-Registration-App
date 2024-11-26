package com.sachit.credentials.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    private String subjectId;
    private String name;
    private String firstName;
    private String lastName;

}
