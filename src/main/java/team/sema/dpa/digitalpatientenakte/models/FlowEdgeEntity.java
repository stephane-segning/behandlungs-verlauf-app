package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "flow_edges")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FlowEdgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "edge_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "start_node_id")
    private FlowChartElementEntity startNode;

    @ManyToOne
    @JoinColumn(name = "end_node_id")
    private FlowChartElementEntity endNode;

    @Column(columnDefinition = "varchar(50)")
    private String name;
}
