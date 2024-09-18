package com.devland.assignment.assignment9.customer.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String active;
}