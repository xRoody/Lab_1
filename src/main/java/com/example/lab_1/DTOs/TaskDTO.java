package com.example.lab_1.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private String eventTime;
    private List<ContactDTO> contactDTOSet;
    private String newContact;
    private Long personId;
    private boolean isComplete=false;
}
