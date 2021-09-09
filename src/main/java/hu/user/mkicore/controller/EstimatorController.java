package hu.user.mkicore.controller;

import hu.user.mkicore.dto.EstimatorParameter;
import hu.user.mkicore.estimators.EstimatorContainer;
import io.spring.guides.gs_producing_web_service.Estimator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EstimatorController {

    private EstimatorContainer estimatorContainer;

    @Value("${estimatorPath}")
    private String estimatorPath;

    public EstimatorController(EstimatorContainer estimatorContainer) {
        this.estimatorContainer = estimatorContainer;
    }

    @PostMapping("/estimator")
    public ResponseEntity<List<Estimator>> addEstimator(@RequestBody EstimatorParameter estimatorParameter) {
        Estimator estimator = new Estimator(estimatorParameter.getName(), 1, estimatorParameter.getHost(), estimatorParameter.getPortNumber());
        estimatorContainer.addEstimator(estimator);
        return new ResponseEntity<>(estimatorContainer.getEstimators(), HttpStatus.OK);
    }

    @GetMapping("/estimator")
    public ResponseEntity<List<Estimator>>  getEstimators() {
        return new ResponseEntity<>(estimatorContainer.getEstimators(), HttpStatus.OK);
    }
}
