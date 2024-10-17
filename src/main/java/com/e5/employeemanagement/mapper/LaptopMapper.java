package com.e5.employeemanagement.mapper;

import com.e5.employeemanagement.dto.LaptopDTO;
import com.e5.employeemanagement.model.Laptop;

/**
 * <p>
 * It is class Mapping DTO to Model and model to DTO for Laptop.
 * </p>
 */
public class LaptopMapper {

    /**
     * <p>
     * It is the method to map the laptop model to laptop dto using laptop dto builder.
     * </p>
     *
     * @param laptop {@link Laptop} it contains laptop details to use a map model to dto.
     * @return {@link LaptopDTO} if laptop does not equal null it contains restricted laptop details,
     *           such as model, brand and os, otherwise null.
     */
    public static LaptopDTO laptopToDTO(Laptop laptop) {
        if (laptop == null) {
            return null;
        }
        return LaptopDTO.builder()
                .model(laptop.getModel())
                .brand(laptop.getBrand())
                .os(laptop.getOs()).build();
    }

    /**
     * <p>
     * It is the method to map the laptop dto to laptop model using laptop builder.
     * </p>
     *
     * @param laptopDTO {@link LaptopDTO} it contains all details of laptop to use a map dto to model.
     * @return {@link Laptop}. it contains all details of the laptop.
     */
    public static Laptop dtoToLaptop(LaptopDTO laptopDTO) {
        if (laptopDTO == null) {
            return null;
        }
        return Laptop.builder()
                .id(laptopDTO.getId())
                .model(laptopDTO.getModel())
                .brand(laptopDTO.getBrand())
                .os(laptopDTO.getOs()).build();
    }
}
