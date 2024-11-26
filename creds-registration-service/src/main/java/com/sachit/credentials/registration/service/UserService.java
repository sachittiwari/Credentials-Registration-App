package com.sachit.credentials.registration.service;

import com.sachit.credentials.registration.entity.User;
import com.sachit.credentials.registration.exception.UserNotFoundException;
import com.sachit.credentials.registration.mapper.UserMapper;
import com.sachit.credentials.registration.model.UserRequestDTO;
import com.sachit.credentials.registration.model.UserResponseDTO;
import com.sachit.credentials.registration.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserOrganizationMappingService userOrganizationMappingService;

    private static final String USER_NOT_FOUND = "User not found with the given id: ";

    /**
     * This method retrieves all Users
     *
     * @return List of All Users
     */
    public List<UserResponseDTO> fetchAllUsers() {
        log.debug("Inside fetchAllUsers");
        List<UserResponseDTO> usersList = new ArrayList<>();
        userRepository.findAll()
                      .forEach(user -> usersList.add(convertUserToUserResponseDTO(user)));
        return usersList;
    }

    /**
     * This method retrieves User based on requested User id
     *
     * @param id of the organization to be retrieved
     * @return User whose information has been retrieved
     * @throws UserNotFoundException User is not found for given id
     */
    public UserResponseDTO getUserById(Long id) throws UserNotFoundException {
        log.debug("Inside getUserById: {}",id);
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return convertUserToUserResponseDTO(user.get());
        }
        throw new UserNotFoundException(USER_NOT_FOUND + id);
    }

    /**
     * This method creates User based on requested data if user is not present
     * Otherwise, returns the existing user and its associated organizations
     *
     * @param request of the user details
     * @return Newly Created or Existing User
     */
    @Transactional
    public UserResponseDTO handleLogin(UserRequestDTO request) {
        log.debug("Inside handleLogin: {}",request);
        User existingUser = userRepository.findBySubjectId(request.getSubjectId());
        if(existingUser != null)
            return convertUserToUserResponseDTO(existingUser);
        User user = userRepository.save(userMapper.toUser(request));
        return convertUserToUserResponseDTO(user);
    }

    /**
     * This method updates User Details based on requested User id
     *
     * @param id of the user to be updated
     * @param request of the user details to be updated
     * @return User whose information has been updated
     * @throws UserNotFoundException User is not found for given id
     */
    public UserResponseDTO updateUserById(Long id, UserRequestDTO request) throws UserNotFoundException {
        log.debug("Inside updateUserById: {}",id);
        if(userRepository.existsById(id)) {
            User user = userMapper.toUser(request);
            user.setId(id);
            return convertUserToUserResponseDTO(userRepository.save(user));
        }
        throw new UserNotFoundException(USER_NOT_FOUND + id);
    }

    /**
     * This method deletes User Details based on requested User id
     *
     * @param id of the user to be deleted
     * @throws UserNotFoundException User is not found for given id
     */
    @Transactional
    public void deleteUserById(Long id) throws UserNotFoundException {
        log.debug("Inside deleteUserById: {}",id);
        if(userRepository.existsById(id)) {
            //Delete user from User
            userRepository.deleteById(id);
            //Delete mapping of the user from User_Organization_Mapping
            userOrganizationMappingService.deleteUserOrganizationMappingByUserId(id);
        }
        else
            throw new UserNotFoundException(USER_NOT_FOUND + id);
    }

    /**
     * This method converts User to User DTO by adding the organization ids
     *
     * @param user details of the user
     * @return User Response DTO containing Org related data
     */
    private UserResponseDTO convertUserToUserResponseDTO(User user) {
        log.debug("Inside convertUserToUserResponseDTO: {}",user);
        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
        userResponseDTO.setOrganizations(userOrganizationMappingService.getAllOrganizationsByUserId(user.getId()));
        return userResponseDTO;
    }
}
