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

    @ManyToMany(mappedBy = "nodes")
    private Set<EdgeEntity> edges;

    @ManyToOne
    @Column(name = "type_id")
    private NodeType type;

    @Column(columnDefinition = "varchar(50)")
    private String name;
}
