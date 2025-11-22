package hexlet.code.service;

import hexlet.code.dto.dtoUser.UserCreateDTO;
import hexlet.code.dto.dtoUser.UserDTO;
import hexlet.code.dto.dtoUser.UserUpdateDTO;

import java.util.List;


public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserCreateDTO userData);
    UserDTO getUserById(Long id);
    UserDTO updateUser(UserUpdateDTO userData, Long id);
    void deleteUser(Long id);
}
