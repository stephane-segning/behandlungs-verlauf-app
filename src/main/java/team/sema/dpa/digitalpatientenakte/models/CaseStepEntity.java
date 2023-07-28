package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "case_steps", indexes = {
        @Index(name = "case_index", columnList = "case_id"),
        @Index(name = "flow_element_index", columnList = "flow_id")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CaseStepEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "step_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private CaseEntity caseE;

    @ManyToOne
    @JoinColumn(name = "flow_id", nullable = false)
    private FlowChartElementEntity flowChart;

    @Column(columnDefinition = "text", nullable = false)
    private String data;
}
