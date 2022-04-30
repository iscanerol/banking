package com.banking.project.controller;

import com.banking.project.model.dto.AddressDto;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.request.AddressRequest;
import com.banking.project.model.request.SingleAddressRequest;
import com.banking.project.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<OperationResult> createAddress(@RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.createAddress(addressDto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AddressDto>> getAddressLst(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(addressService.findAddress(email));
    }
    @DeleteMapping("/deleteList")
    public ResponseEntity<OperationResult> deleteAddressList(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(addressService.removeAllAddress(email));
    }
    @PutMapping("/update")
    public ResponseEntity<OperationResult> updateAddress(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequest));
    }
    @PutMapping("/singleUpdate")
    public ResponseEntity<AddressDto> singleUpdateAddress(@RequestBody SingleAddressRequest singleAddressRequest) {
        return ResponseEntity.ok(addressService.updateSingleAddress(singleAddressRequest));
    }
    @DeleteMapping("/singleDelete")
    public ResponseEntity<List<AddressDto>> singleDeleteAddress(@RequestParam Long id){
        return ResponseEntity.ok(addressService.singleRemoveAddress(id));
    }


}
