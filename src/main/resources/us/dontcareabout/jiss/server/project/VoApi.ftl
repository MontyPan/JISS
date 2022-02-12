package ${project.rootPackage}.server.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${project.rootPackage}.server.repo.${voName}Repo;
import ${project.rootPackage}.shared.vo.${voName};

@RestController
@RequestMapping("${voName}")
public class ${voName}Api {
	@Autowired ${voName}Repo repo;

	@GetMapping("/all")
	public List<${voName}> all() {
		return repo.findAll();
	}

	@PostMapping("/batchCreate")
	public void batchCreate(@RequestBody List<${voName}> data) {
		repo.saveAll(data);
	}
}
