package hu.user.mkicore.estimators;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class EstimatorContainer {

    private Map<Integer, Integer> estimatorWeightById = new HashMap<>();

    public Map<Integer, Integer> addEstimator(int estimatorId, int estimatorWeight) {
        estimatorWeightById.put(estimatorId, estimatorWeight);
        return estimatorWeightById;
    }
}
