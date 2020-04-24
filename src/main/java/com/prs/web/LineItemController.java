package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.LineItem;
import com.prs.db.LineItemRepository;


@RestController
@RequestMapping("/lineitems")
public class LineItemController {
	@Autowired
	private LineItemRepository lineItemRepo;

	// list all items
	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<LineItem> requests = lineItemRepo.findAll();
		if (requests.size() > 0) {
			jr = JsonResponse.getInstance(requests);
		} else {
			jr = JsonResponse.getErrorInstance("No Line Item found.");
		}
		return jr;
	}

	// get /list one item
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		Optional<LineItem> request = lineItemRepo.findById(id);
		if (request.isPresent()) {
			jr = JsonResponse.getInstance(request.get());
		} else {
			jr = JsonResponse.getErrorInstance("No Line Item found for id: " + id);
		}
		return jr;
	}

	// "create method
	@PostMapping("/")
	public JsonResponse createLineItem(@RequestBody LineItem l) {
		JsonResponse jr = null;
		try {
			l = lineItemRepo.save(l);
			jr = JsonResponse.getInstance(l);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating Line Item: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method
	@PutMapping("/")
	public JsonResponse updateLineItem(@RequestBody LineItem l) {
		JsonResponse jr = null;
		try {
			l = lineItemRepo.save(l);
			jr = JsonResponse.getInstance(l);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating LineItem: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteLineItem(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			lineItemRepo.deleteById(id);
			jr = JsonResponse.getInstance("Line Item id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Line Item: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

}

