package org.springcloud.users.domain.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private String name;
    private String lastName;
}
