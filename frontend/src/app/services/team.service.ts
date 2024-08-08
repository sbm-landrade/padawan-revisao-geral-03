import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Team } from '../models/team';
@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private apiUrl = 'http://localhost:8080/api/teams';

  constructor(private http: HttpClient) {}
     // Método para obter todos os times
  getAllTeams(): Observable<Team[]> {
    return this.http.get<Team[]>(this.apiUrl);
  }

  // Método para buscar times com filtros
  searchTeams(teamName?: string, country?: string, coachName?: string): Observable<Team[]> {
    let params = new HttpParams();
    if (teamName) params = params.set('teamName', teamName);
    if (country) params = params.set('country', country);
    if (coachName) params = params.set('coachName', coachName);

    return this.http.get<Team[]>(this.apiUrl, { params });
  }
}
