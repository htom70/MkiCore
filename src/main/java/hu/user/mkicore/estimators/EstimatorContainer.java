package hu.user.mkicore.estimators;

import io.spring.guides.gs_producing_web_service.Estimator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class EstimatorContainer {

    private List<Estimator> estimators = new ArrayList<>();

//    public EstimatorContainer() {
//        Estimator estimator = new Estimator("sample", 1, "http://localhost:", 8084);
//        addEstimator(estimator);
//    }

    public List<Estimator> addEstimator(Estimator estimator) {
        estimators.add(estimator);
        return estimators;
    }


}
