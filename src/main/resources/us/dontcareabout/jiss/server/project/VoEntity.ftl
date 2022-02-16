package ${project.rootPackage}.shared.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import us.dontcareabout.gwt.shared.vo.HasId;

@Entity
public class ${voName} implements HasId<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Override
	public Long getId() { return id; }
	
	@Override
	public void setId(Long id) { this.id = id; }

	////////////////
	
}
