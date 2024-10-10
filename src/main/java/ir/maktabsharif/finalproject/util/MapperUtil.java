package ir.maktabsharif.finalproject.util;

import ir.maktabsharif.finalproject.dto.*;
import ir.maktabsharif.finalproject.entities.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperUtil {
    private final ModelMapper modelMapper;

    public Customer convertToEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }

    public CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public SpecialistDto convertToDto(Specialist specialist) {
        return modelMapper.map(specialist, SpecialistDto.class);
    }

    public Specialist convertToEntity(SpecialistDto specialistDto) {
        return modelMapper.map(specialistDto, Specialist.class);
    }

    public SubTask convertToEntity(SubTaskDto subTaskDto) {
        return modelMapper.map(subTaskDto, SubTask.class);
    }

    public SubTaskDto convertToDto(SubTask subTask) {
        return modelMapper.map(subTask, SubTaskDto.class);
    }

    public Suggestions convertToEntity(SuggestionDto suggestionDto) {
        return modelMapper.map(suggestionDto, Suggestions.class);
    }

    public SuggestionDto convertToDto(Suggestions suggestions) {
        return modelMapper.map(suggestions, SuggestionDto.class);
    }

    public UserDto convertToDto(Users users) {
        return modelMapper.map(users, UserDto.class);
    }

    public Users convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, Users.class);
    }
}
