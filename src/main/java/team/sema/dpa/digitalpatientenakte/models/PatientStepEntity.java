package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "patient_steps")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PatientStepEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "step_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private CaseEntity caseE;

    @ManyToOne
    @JoinColumn(name = "flow_id", nullable = false)
    private FlowChartElementEntity flowChart;

    @Column(columnDefinition = "text")
    private String data;
}
