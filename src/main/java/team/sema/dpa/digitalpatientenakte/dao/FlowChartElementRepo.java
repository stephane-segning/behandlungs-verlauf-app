package team.sema.dpa.digitalpatientenakte.dao;

import team.sema.dpa.digitalpatientenakte.models.FlowChartElementEntity;

import java.util.List;
import java.util.UUID;

public interface FlowChartElementRepo {
    FlowChartElementEntity findById(UUID id);

    List<FlowChartElementEntity> findAll();

    FlowChartElementEntity save(FlowChartElementEntity model);

    void delete(FlowChartElementEntity model);

}

