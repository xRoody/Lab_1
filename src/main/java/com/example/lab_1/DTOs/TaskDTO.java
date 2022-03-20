package com.example.lab_1.DTOs;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class TaskDTO {
    private String name;
    private String description;
    private LocalDateTime eventTime;
    private Set<ContactDTO> contactDTOSet;
}
