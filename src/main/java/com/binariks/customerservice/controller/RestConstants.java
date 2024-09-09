package com.binariks.customerservice.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestConstants {
    public static final String CUSTOMERS_PATH = "/customers";
    public static final String ID_PATH= "/{id}";
    public static final String HISTORY_PATH= "/history";
    public static final String API_PATH = "/api";
}
