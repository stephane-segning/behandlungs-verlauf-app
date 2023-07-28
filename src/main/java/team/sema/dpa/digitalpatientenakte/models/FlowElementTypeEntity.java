package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "flow_element_types")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FlowElementTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "type_id")
    private UUID id;

    @Column(columnDefinition = "varchar(50)")
    private String name;

    @Column(columnDefinition = "varchar(50)", name = "personal_name", nullable = false)
    private String personalName;

}
