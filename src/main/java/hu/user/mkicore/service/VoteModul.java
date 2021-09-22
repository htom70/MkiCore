package hu.user.mkicore.service;

import hu.user.mkicore.domain.Fraud;
import hu.user.mkicore.domain.CommulatedResponseFromEstimator;
import hu.user.mkicore.domain.ResponseFromSingleEstimator;
import hu.user.mkicore.estimators.EstimatorContainer;
import hu.user.mkicore.repository.FraudRepository;
import io.spring.guides.gs_producing_web_service.DetectionRequest;
import io.spring.guides.gs_producing_web_service.DetectionResponse;
import io.spring.guides.gs_producing_web_service.GenerateDetectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VoteModul {

    @Value("${estimatorPath}")
    private String estimatorPath;

    private EstimatorContainer estimatorContainer;
    private Prediction prediction;
    private FraudRepository fraudRepository;

    @Autowired
    public VoteModul(EstimatorContainer estimatorContainer, Prediction singlePrediction, FraudRepository fraudRepository) {
        this.estimatorContainer = estimatorContainer;
        this.prediction = singlePrediction;
        this.fraudRepository = fraudRepository;
    }

    public DetectionResponse voting(GenerateDetectionRequest generateDetectionRequest) {
        DetectionRequest detectionRequest = generateDetectionRequest.getDetectionRequest();
        CommulatedResponseFromEstimator commulatedResponseFromEstimator = prediction.getPrediction(detectionRequest);
        DetectionResponse detectionResponse = evaulate(commulatedResponseFromEstimator);
        return detectionResponse;
    }

    private DetectionResponse evaulate(CommulatedResponseFromEstimator commulatedResponses) {
        DetectionResponse summaryzedResponse = new DetectionResponse();
        Map<Integer, Integer> estimatorWeightById = estimatorContainer.getEstimatorWeightById();
        int noOKNumber = 0;
        int oKNumber = 0;
        double noOKProbability = 1;
        double oKProbability = 1;

        for (Map.Entry<Integer, ResponseFromSingleEstimator> entry : commulatedResponses.getResponse().entrySet()) {
            int estimatorId = entry.getKey();
            ResponseFromSingleEstimator response = entry.getValue();
            int weight = estimatorWeightById.get(estimatorId);
            if (response.getPrediction() == 1) {
                noOKNumber += weight;
                noOKProbability *= weight * response.getPositiveProbability();
            } else {
                oKNumber += weight;
                oKProbability *= weight * response.getNegativeProbability();
            }
            if (oKNumber > noOKNumber) {
                summaryzedResponse.setPrediction(0);
                summaryzedResponse.setNegativeProbability(oKProbability);
                summaryzedResponse.setPositiveProbability(1 - oKProbability);
            } else if (oKNumber < noOKNumber) {
                summaryzedResponse.setPrediction(1);
                summaryzedResponse.setPositiveProbability(noOKProbability);
                summaryzedResponse.setNegativeProbability(1 - noOKProbability);
            }
        }
        return summaryzedResponse;
    }


}
