package com.homework.goi.np.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.homework.goi.np.entity.NationalParkIndia;
import com.homework.goi.np.exception.NationalParkAlreadyExistException;
import com.homework.goi.np.exception.NationalParkNotFoundException;
import com.homework.goi.np.service.NationalParkIndiaService;

@RestController
public class NationalParkIndiaController {

	@Autowired
	private NationalParkIndiaService service;

	@PostMapping(value = "/saveNationalPark")
	public ResponseEntity<Boolean> saveNationalPark(@Valid @RequestBody NationalParkIndia nationalParkIndia) {

		boolean isAdded = service.saveNationalPark(nationalParkIndia);

		if (isAdded) {
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		} else {
			throw new NationalParkAlreadyExistException("National park already Exist");
		}
	}
	@PutMapping(value = "/updateNationalPark")
	public ResponseEntity<Boolean> updateNationalPark(@Valid @RequestBody NationalParkIndia nationalParkIndia) {

		boolean isUpdated = service.updateNationalPark(nationalParkIndia);

		if (isUpdated) {
			return new ResponseEntity<Boolean>(isUpdated, HttpStatus.ACCEPTED);
		} else {;
			throw new NationalParkNotFoundException("National park not found for Id:" + nationalParkIndia.getParkId());
		}
	}
	@DeleteMapping(value = "/DeleteNationalPark")
	public ResponseEntity<Boolean> deleteNationalPark(int parkId){
		
		boolean isDeleted = service.deleteNationalPark(parkId);
		
		if(isDeleted) {
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		}else {
			throw new NationalParkNotFoundException("National park not found for Id:" + parkId);
		}
	}
	@GetMapping(value = "/getAllNationalPark")
	public ResponseEntity<List<NationalParkIndia>> getAllNationalPark(){
			
		List<NationalParkIndia> list = service.getAllNationalPark();
		
		if(!list.isEmpty()) {
			return  new ResponseEntity<List<NationalParkIndia>>(list, HttpStatus.OK);
		}else {
			throw new NationalParkNotFoundException("National parks not found"); 
		}
	}
	
	@PostMapping(value = "/uploadSheet")
	public ResponseEntity<Map<String, String>> uploadSheet(@RequestParam CommonsMultipartFile file, HttpSession session ) {
		
		Map<String, String> map = service.uploadSheet(file, session);
		
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}
	
	@GetMapping(value = "/sortNationalPark")
	public ResponseEntity<List<NationalParkIndia>> sortNationalPark(@RequestParam String sortBy){
			
		List<NationalParkIndia> list = service.sortNationalPark(sortBy);
		
		if(!list.isEmpty()) {
			return  new ResponseEntity<List<NationalParkIndia>>(list, HttpStatus.OK);
		}else {
			throw new NationalParkNotFoundException("National parks not found"); 
		}
	}
	@GetMapping(value = "/getNationalParkByState")
	public ResponseEntity<List<NationalParkIndia>> getNationalParkByState(String state){
			
		List<NationalParkIndia> list = service.getNationalParkByState(state);
		
		if(!list.isEmpty()) {
			return  new ResponseEntity<List<NationalParkIndia>>(list, HttpStatus.OK);
		}else {
			throw new NationalParkNotFoundException("National parks not found");
		}
	}
	@GetMapping(value = "/getNationalParkByYear")
	public ResponseEntity<List<NationalParkIndia>> getNationalParkByYear(int establishmentYear){
			
		List<NationalParkIndia> list = service.getNationalParkByYear(establishmentYear);
		
		if(!list.isEmpty()) {
			return  new ResponseEntity<List<NationalParkIndia>>(list, HttpStatus.OK);
		}else {
			throw new NationalParkNotFoundException("National parks not found"); 
		}
	}
	@GetMapping(value = "/exportToExcel")
	public ResponseEntity<String> exportToExcel(HttpSession session)  {
		String msg = service.exportToExcel(session);
		return new ResponseEntity<String>(msg, HttpStatus.OK);

	}
	
}
