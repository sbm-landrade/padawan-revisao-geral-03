package br.com.api.futebol.repository;

import javax.sql.DataSource;

import br.com.api.futebol.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepository {

	private final DataSource dataSource;

	// Criar conexão
	@Autowired
	public TeamRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void save(Team team) {
		String sql;
		if (team.getId() == null) {
			sql = "INSERT INTO teams (team_name, country, coach_name, team_value) VALUES (?, ?, ?, ?)";
		} else {
			sql = "UPDATE teams SET team_name = ?, country = ?, coach_name = ?, team_value = ? WHERE id = ?";
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, team.getTeamName());
			statement.setString(2, team.getCountry());
			statement.setString(3, team.getCoachName());
			statement.setBigDecimal(4, team.getTeamValue());

			if (team.getId() != null) {
				statement.setInt(5, team.getId());
			}

			statement.executeUpdate();

			if (team.getId() == null) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						team.setId(generatedKeys.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Team> findAll() {
		List<Team> teams = new ArrayList<>();
		String sql = "SELECT * FROM teams";

		System.out.println("Iniciando a consulta ao banco de dados");
		
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Team team = mapRow(resultSet);
				teams.add(team);
				System.out.println("Time adicionado: " + team.getTeamName());
			}
		} catch (SQLException e) {
			System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
			e.printStackTrace();
		}
		 System.out.println("Consulta finalizada, total de times encontrados: " + teams.size());
		return teams;
	}

	public Optional<Team> findById(Integer id) {
		String sql = "SELECT * FROM teams WHERE id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(mapRow(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public void deleteById(Integer id) {
		String sql = "DELETE FROM teams WHERE id = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Team mapRow(ResultSet resultSet) throws SQLException {
		Team team = new Team();
		team.setId(resultSet.getInt("id"));
		team.setTeamName(resultSet.getString("team_name"));
		team.setCountry(resultSet.getString("country"));
		team.setCoachName(resultSet.getString("coach_name"));
		team.setTeamValue(resultSet.getBigDecimal("team_value"));
		team.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
		team.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));
		return team;
	}
}
