package com.dhl.test.shared;


import jakarta.inject.Singleton;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Singleton
public class StateHolder {

    private String pageTitle;
    private String url;
}
