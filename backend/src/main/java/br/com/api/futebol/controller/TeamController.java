package br.com.api.futebol.controller;

import br.com.api.futebol.model.*;
import br.com.api.futebol.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

	@RestController
	@RequestMapping("/api/teams")
	public class TeamController {

	    @Autowired
	    private TeamService teamService;
	    //listar todos os times
	    @CrossOrigin(origins = "http://localhost:4200")
	    @GetMapping
	    public List<Team> getAllTeams() {
	        return teamService.getAllTeams();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Team> getTeamById(@PathVariable Integer id) {
	        Optional<Team> team = teamService.getTeamById(id);
	        return team.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
	        teamService.saveTeam(team);
	        return ResponseEntity.status(HttpStatus.CREATED).body(team);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Team> updateTeam(@PathVariable Integer id, @RequestBody Team team) {
	        if (teamService.getTeamById(id).isPresent()) {
	            team.setId(id);
	            teamService.saveTeam(team);
	            return ResponseEntity.ok(team);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
	        if (teamService.getTeamById(id).isPresent()) {
	            teamService.deleteTeam(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}
