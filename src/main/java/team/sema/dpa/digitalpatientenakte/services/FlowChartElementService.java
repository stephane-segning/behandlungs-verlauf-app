package team.sema.dpa.digitalpatientenakte.services;

import lombok.NoArgsConstructor;
import lombok.Setter;
import team.sema.dpa.digitalpatientenakte.dao.FlowChartElementRepo;
import team.sema.dpa.digitalpatientenakte.models.FlowChartElementEntity;
import team.sema.dpa.digitalpatientenakte.state.Autowired;
import team.sema.dpa.digitalpatientenakte.state.Component;

import java.util.List;
import java.util.UUID;

@Component
@Setter
@NoArgsConstructor
public class FlowChartElementService {

    @Autowired
    private FlowChartElementRepo repository;

    public FlowChartElementEntity getCase(UUID id) {
        return repository.findById(id);
    }

    public List<FlowChartElementEntity> getAllFlowElements() {
        return repository.findAll();
    }

    public FlowChartElementEntity saveCase(FlowChartElementEntity model) {
        return repository.save(model);
    }

    public void deleteCase(FlowChartElementEntity model) {
        repository.delete(model);
    }
}

