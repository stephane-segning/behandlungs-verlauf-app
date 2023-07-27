package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "flow_chart_elements")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FlowChartElementEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "node_id")
    private UUID id;

    @OneToMany(mappedBy = "startNode")
    private Set<FlowEdgeEntity> startingEdges;

    @OneToMany(mappedBy = "endNode")
    private Set<FlowEdgeEntity> endingEdges;

    @ManyToOne
    @Column(name = "type_id")
    private FlowElementType type;

    @Column(columnDefinition = "varchar(50)")
    private String name;
}
