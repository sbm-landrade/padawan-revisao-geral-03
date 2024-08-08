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
  teamValueMin?: number;
  teamValueMax?: number;
  createdAtFrom?: string;
  createdAtTo?: string;
  updatedAtFrom?: string;
  updatedAtTo?: string;

  constructor(private teamService: TeamService) { }

  ngOnInit(): void {
    this.loadTeams();
  }

  loadTeams(): void {
    this.teamService.getAllTeams().subscribe(data => {
      this.teams = data;
    });
  }

  searchTeams() {
    this.teamService.getFilteredTeams(
      this.teamName,
      this.country,
      this.coachName,
      this.teamValueMin,
      this.teamValueMax,
      this.createdAtFrom,
      this.createdAtTo,
      this.updatedAtFrom,
      this.updatedAtTo
    ).subscribe(teams => {
      this.teams = teams;
    });
  }

}
