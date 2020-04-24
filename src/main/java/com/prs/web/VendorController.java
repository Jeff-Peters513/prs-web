package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@RestController
@RequestMapping("/vendors")
public class VendorController {

	@Autowired
	private VendorRepository vendorRepo;

	// list all items
	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<Vendor> vendors = vendorRepo.findAll();
		if (vendors.size() > 0) {
			jr = JsonResponse.getInstance(vendors);
		} else {
			jr = JsonResponse.getErrorInstance("No Vendor found.");
		}
		return jr;
	}

	// get /list one item
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		Optional<Vendor> vendor = vendorRepo.findById(id);
		if (vendor.isPresent()) {
			jr = JsonResponse.getInstance(vendor.get());
		} else {
			jr = JsonResponse.getErrorInstance("No Vendor found for id: " + id);
		}
		return jr;
	}

	// "create method
	@PostMapping("/")
	public JsonResponse createVendor(@RequestBody Vendor v) {
		JsonResponse jr = null;
		try {
			v = vendorRepo.save(v);
			jr = JsonResponse.getInstance(v);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating Vendor: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method
	@PutMapping("/")
	public JsonResponse updateVendor(@RequestBody Vendor v) {
		JsonResponse jr = null;
		try {
			v = vendorRepo.save(v);
			jr = JsonResponse.getInstance(v);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating Vendor: " + e.getMessage());
			e.printStackTrace();
		}

		return jr;

	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteVendor(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			vendorRepo.deleteById(id);
			jr = JsonResponse.getInstance("Vendor id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Vendor: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

}
