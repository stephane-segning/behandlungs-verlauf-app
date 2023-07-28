package team.sema.dpa.digitalpatientenakte.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.io.Serializable;

@Data
@Entity
@Table(name = "flow_case_step_views")
@Immutable
@Cacheable
@Subselect("""
        SELECT cs.data,
        cs.case_id,
          f.flow_id,
          f.name AS flow_name,
          t.name AS type_name,
          t.personal_name AS type_personal_name,
          c.pat_id
         FROM case_steps cs
           LEFT JOIN flow_chart_elements f ON f.flow_id = cs.flow_id
           LEFT JOIN flow_element_types t ON t.type_id = f.type_id
           LEFT JOIN cases c ON c.case_id = cs.case_id;
         """)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FlowCaseStepView implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String data;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private CaseEntity aCase;

    @ManyToOne
    @JoinColumn(name = "flow_id")
    private FlowChartElementEntity flow;

    @Column(name = "flow_name")
    private String flowName;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "type_personal_name")
    private String personalName;

}
