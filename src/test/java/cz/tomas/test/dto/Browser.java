package cz.tomas.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Browser {

    private String name;
    private String os;
}
