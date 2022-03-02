package com.example.renderfarm.service;

import com.example.renderfarm.api.request.RegisterRequest;
import com.example.renderfarm.api.response.IdResponse;
import com.example.renderfarm.exception.UserFoundException;
import com.example.renderfarm.model.Person;
import com.example.renderfarm.model.enums.Role;
import com.example.renderfarm.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonRepository personRepository;

    public IdResponse registration(RegisterRequest registerRequest) throws UserFoundException {
        Optional<Person> person = personRepository.findByEmail(registerRequest.getEmail());
        if (person.isPresent()) throw new UserFoundException();
        Person newPerson = new Person()
                .setEmail(registerRequest.getEmail())
                .setPassword(registerRequest.getPassword())
                .setRole(Role.USER);
        personRepository.save(newPerson);
        return new IdResponse()
                .setPersonId(newPerson.getId());
    }
}
