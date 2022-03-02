package com.example.renderfarm.service;

import com.example.renderfarm.api.request.AuthRequest;
import com.example.renderfarm.api.request.RegisterRequest;
import com.example.renderfarm.api.response.AuthData;
import com.example.renderfarm.api.response.DataResponse;
import com.example.renderfarm.api.response.IdResponse;
import com.example.renderfarm.exception.UserFoundException;
import com.example.renderfarm.model.Person;
import com.example.renderfarm.model.enums.Role;
import com.example.renderfarm.repo.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    /**
     * registration
     *
     * @param registerRequest
     * @return
     * @throws UserFoundException
     */
    public IdResponse registration(RegisterRequest registerRequest) throws UserFoundException {
        Optional<Person> person = personRepository.findByEmail(registerRequest.getEmail());
        if (person.isPresent()) throw new UserFoundException();
        Person newPerson = new Person()
                .setEmail(registerRequest.getEmail())
                .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .setRole(Role.USER);
        personRepository.save(newPerson);
        return new IdResponse()
                .setPersonId(newPerson.getId());
    }

    /**
     * login
     *
     * @param authRequest
     * @return
     */
    public DataResponse<AuthData> login(AuthRequest authRequest) throws UserFoundException {
        Optional<Person> optionalPerson = personRepository.findByEmail(authRequest.getEmail());
        if (optionalPerson.isEmpty()) {
            throw new UserFoundException();
        } else if (!passwordEncoder.matches(authRequest.getPassword(), optionalPerson.get().getPassword())) {
            throw new UserFoundException();
        }
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new DataResponse<AuthData>()
                .setTimestamp(Instant.now().toEpochMilli())
                .setData(new AuthData().setEmail(authRequest.getEmail()));
    }
}
