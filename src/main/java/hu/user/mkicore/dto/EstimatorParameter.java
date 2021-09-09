package hu.user.mkicore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EstimatorParameter {

    private String name;
    private String host;
    private int portNumber;

}
