package com.e5.employeemanagement.mapper;

import com.e5.employeemanagement.dto.ToolDTO;
import com.e5.employeemanagement.model.Tool;

/**
 * <p>
 * It is class Mapping DTO to Model and model to DTO for Tool.
 * </p>
 */
public class ToolMapper {
    /**
     * <p>
     * It is the method to map the tool model to tool dto using tool dto builder.
     * </p>
     *
     * @param tool {@link Tool} it contains tool details to use a map model to dto.
     * @return {@link ToolDTO} if tool does not equal null it contains restricted tool details,
     *           such as model, brand and os, otherwise null.
     */
    public static ToolDTO toolToDTO(Tool tool) {
        if (tool == null) {
            return null;
        }
        return ToolDTO.builder()
                .id(tool.getId())
                .name(tool.getName())
                .version(tool.getVersion())
                .type(tool.getType()).build();
    }

    /**
     * <p>
     * It is the method to map the tool dto to tool model using tool builder.
     * </p>
     *
     * @param toolDTO {@link ToolDTO} it contains all details of tool to use a map dto to model.
     * @return {@link Tool}. it contains all details of the tool.
     */
    public static Tool dtoToTool(ToolDTO toolDTO) {
        if (toolDTO == null) {
            return null;
        }
        return Tool.builder()
                .id(toolDTO.getId())
                .name(toolDTO.getName())
                .version(toolDTO.getVersion())
                .type(toolDTO.getType()).build();
    }
}
