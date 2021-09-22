package hu.user.mkicore.controller;

import hu.user.mkicore.dto.EstimatorParameter;
import hu.user.mkicore.estimators.EstimatorContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class EstimatorController {

    private EstimatorContainer estimatorContainer;

    @Value("${estimatorPath}")
    private String estimatorPath;

    public EstimatorController(EstimatorContainer estimatorContainer) {
        this.estimatorContainer = estimatorContainer;
    }

    @PostMapping("/estimator")
    public ResponseEntity<Map<Integer, Integer>> addEstimator(@RequestBody EstimatorParameter estimatorParameter) {
        estimatorContainer.addEstimator(estimatorParameter.getEstimatorId(), estimatorParameter.getWeight());
        return new ResponseEntity<>(estimatorContainer.getEstimatorWeightById(),HttpStatus.OK);
    }

    @GetMapping("/estimator")
    public ResponseEntity<Map<Integer, Integer>>  getEstimators() {
        return new ResponseEntity<>(estimatorContainer.getEstimatorWeightById(), HttpStatus.OK);
    }
}
