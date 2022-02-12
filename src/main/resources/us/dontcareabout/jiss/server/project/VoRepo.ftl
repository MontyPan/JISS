package ${project.rootPackage}.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ${project.rootPackage}.shared.vo.${voName};

@Repository
@RepositoryRestResource(path = "${voName}")
public interface ${voName}Repo extends JpaRepository<${voName}, Long> {}
