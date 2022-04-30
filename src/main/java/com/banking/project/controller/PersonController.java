package com.banking.project.controller;

import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.dto.PersonDto;
import com.banking.project.model.dto.PersonUpdateDto;
import com.banking.project.model.entity.PersonEntity;
import com.banking.project.model.request.PersonRequest;
import com.banking.project.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class PersonController {

    private final PersonService personService;

    @PostMapping("/createPerson")
    public ResponseEntity<OperationResult>createNewPerson(@RequestBody PersonDto personDto){
        return ResponseEntity.ok(personService.createPerson(personDto));

    }
    @PutMapping("/updatePerson")
    public ResponseEntity<OperationResult>updatePerson(@RequestBody PersonUpdateDto updateRequest){
        return ResponseEntity.ok(personService.updatePerson(updateRequest));

    }
    @PostMapping ("/validPerson")
    public ResponseEntity<OperationResult>createNewPerson(@Valid @RequestBody  PersonRequest personRequest){
        return ResponseEntity.ok(personService.validationCreatePerson(personRequest));

    }
    @DeleteMapping ("/deletePerson")
    public ResponseEntity<OperationResult>deletePerson(@RequestParam  Long id){
        return ResponseEntity.ok(personService.removePerson(id));

    }
    @GetMapping ("/findPerson")
    public ResponseEntity<PersonDto>findPerson(@RequestParam String email){
        return ResponseEntity.ok(personService.findPerson(email));

    }

    @GetMapping ("/personList")
    public ResponseEntity<List<PersonDto>> personList() {
        return ResponseEntity.ok(personService.personRequestList());
    }
}
