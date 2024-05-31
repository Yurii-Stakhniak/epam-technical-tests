package com.evri.interview.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class Courier {
    long id;
    @FullName
    @NotEmpty(message = "Name cannot be empty")
    String name;
    boolean active;
}
