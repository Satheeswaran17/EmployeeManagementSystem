package com.e5.employeemanagement.mapper;

import com.e5.employeemanagement.dto.TeamDTO;
import com.e5.employeemanagement.model.Team;

/**
 * <p>
 * It is class Mapping DTO to Model and model to DTO for Team.
 * </p>
 */
public class TeamMapper {
    /**
     * <p>
     * It is the method to map the team model to team dto using team dto builder.
     * </p>
     *
     * @param team {@link Team} it contains team details to use a map model to dto.
     * @return {@link TeamDTO} if team does not equal null it contains restricted team details,
     *           such as domain, project and lead name, otherwise null.
     */
    public static TeamDTO teamToDTO(Team team) {
        if (team == null) {
            return null;
        }
        return TeamDTO.builder()
                .domain(team.getDomain())
                .project(team.getProject())
                .leadName(team.getLeadName()).build();
    }

    /**
     * <p>
     * It is the method to map the team dto to team model using team builder.
     * </p>
     *
     * @param teamDTO {@link TeamDTO} it contains all details of team to use a map dto to model.
     * @return {@link Team}. it contains all details of the team.
     */
    public static Team dtoToTeam(TeamDTO teamDTO) {
        if (teamDTO == null) {
            return null;
        }
        return Team.builder()
                .id(teamDTO.getId())
                .domain(teamDTO.getDomain())
                .leadName(teamDTO.getLeadName()).build();
    }
}
