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

  constructor(private teamService: TeamService) { }

  ngOnInit(): void {
    this.teamService.getAllTeams().subscribe(data => {
      this.teams = data;
    });
  }
}
