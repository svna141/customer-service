package com.binariks.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    M("Male"),
    F("Female");

    private final String caption;
}
