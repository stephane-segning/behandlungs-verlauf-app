package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "edges")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EdgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "edge_id")
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "edges_nodes",
            joinColumns = @JoinColumn(name = "edge_id"),
            inverseJoinColumns = @JoinColumn(name = "node_id")
    )
    private Set<FlowChartElementEntity> nodes;

    @Column(columnDefinition = "varchar(50)")
    private String name;
}
