package com.prs.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.Request;
import com.prs.db.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {

	@Autowired
	private RequestRepository requestRepo;

	// list all items
	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<Request> requests = requestRepo.findAll();
		if (requests.size() > 0) {
			jr = JsonResponse.getInstance(requests);
		} else {
			jr = JsonResponse.getErrorInstance("No Request found.");
		}
		return jr;
	}

	// list all requests in review status and not the logged in user whom is
	// reviewing
	@GetMapping("/list-review/{id}")
	public JsonResponse requestListReviewNotUserID(@PathVariable int id) {
		JsonResponse jr = null;
		List<Request> limitRequest = requestRepo.findByStatusAndUserIdNot("Review", id);
		if (limitRequest.size() > 0) {
			jr = JsonResponse.getInstance(limitRequest);
		} else {
			jr = JsonResponse.getErrorInstance("No Requests in Review found.");
		}
		return jr;
	}

	// get /list one item
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		Optional<Request> request = requestRepo.findById(id);
		if (request.isPresent()) {
			jr = JsonResponse.getInstance(request.get());
		} else {
			jr = JsonResponse.getErrorInstance("No Request found for id: " + id);
		}
		return jr;
	}

	// "create method
	@PostMapping("/")
	public JsonResponse createRequest(@RequestBody Request r) {
		System.out.println(r);
		JsonResponse jr = null;
		r.setStatus("New");
		r.setSubmittedDate(LocalDateTime.now());
		try {
			r = requestRepo.save(r);
			jr = JsonResponse.getInstance(r);
		} catch (DataIntegrityViolationException dive) {
			jr = JsonResponse.getErrorInstance(dive.getRootCause().getMessage());
			dive.printStackTrace();
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error creating Request: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method
	@PutMapping("/")
	public JsonResponse updateRequest(@RequestBody Request r) {
		JsonResponse jr = null;
		try {
			r = requestRepo.save(r);
			jr = JsonResponse.getInstance(r);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating Request: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method - submit and change status to "review" or if under $50.00 to
	// "approved"
	@PutMapping("/submit-review")
	public JsonResponse updateRequestSubmit(@RequestBody Request r) {
		JsonResponse jr = null;
		try {

			if (r.getTotal() <= 50.00) {
				r.setStatus("Approved");
			} else {
				r.setStatus("Review");
			}

			r.setSubmittedDate(LocalDateTime.now());
			r = requestRepo.save(r);
			jr = JsonResponse.getInstance(r);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating Request to Review Status: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method - submit for approval
	@PutMapping("/approve")
	public JsonResponse updateRequestApprove(@RequestBody Request r) {
		JsonResponse jr = null;
		r.setStatus("Approved");
		try {
			r = requestRepo.save(r);
			jr = JsonResponse.getInstance(r);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating Request: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// update method - Reject
	@PutMapping("/reject")
	public JsonResponse updateRequestReject(@RequestBody Request r) {
		JsonResponse jr = null;
		r.setStatus("Rejected");
		// Reason for rejection comes from front end
		//maybe this will work? r.setReasonForRejection(getReasonForRejection());
		try {
			r = requestRepo.save(r);
			jr = JsonResponse.getInstance(r);
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error updating Request: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	@DeleteMapping("/{id}")
	public JsonResponse deleteRequest(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			requestRepo.deleteById(id);
			jr = JsonResponse.getInstance("Request id: " + id + " deleted successfully.");
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Request: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

}
