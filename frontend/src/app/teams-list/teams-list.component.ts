import { Component, OnInit } from '@angular/core';
import { TeamService } from '../services/team.service';
import { Team } from '../models/team';

@Component({
  selector: 'app-teams-list',
  templateUrl: './teams-list.component.html',
  styleUrls: ['./teams-list.component.css']
})
export class TeamsListComponent implements OnInit {

  teams: Team[] = [];
  teamName: string = '';
  country: string = '';
  coachName: string = '';

  constructor(private teamService: TeamService) { }

  ngOnInit(): void {
    this.loadTeams();
  }

  loadTeams(): void {
    this.teamService.getAllTeams().subscribe(data => {
      this.teams = data;
    });
  }

  searchTeams(): void {
    this.teamService.searchTeams(this.teamName, this.country, this.coachName).subscribe(data => {
      this.teams = data;
    });
  }
}
