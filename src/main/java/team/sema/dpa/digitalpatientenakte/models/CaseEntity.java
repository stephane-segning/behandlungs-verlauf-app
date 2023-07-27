package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "cases")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "case_id")
    private UUID id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caseE")
    private Set<PatientStepEntity> patient;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", updatable = false)
    private Date date;

    @Column(name = "arrived_by", updatable = false)
    private String arrivedBy;
}
