package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.LineItem;
import com.prs.business.Request;
import com.prs.db.LineItemRepository;
import com.prs.db.RequestRepository;

@RestController
@RequestMapping("/line-items")
public class LineItemController {
	@Autowired
	private LineItemRepository lineItemRepo;
	@Autowired
	private RequestRepository requestRepo;

	// list all items
	@GetMapping("/")
	public JsonResponse list() {
		JsonResponse jr = null;
		List<LineItem> lineItems = lineItemRepo.findAll();
		if (lineItems.size() > 0) {
			jr = JsonResponse.getInstance(lineItems);
		} else {
			jr = JsonResponse.getErrorInstance("No Line Item found.");
		}
		return jr;
	}

	// list all items for purchase request per id
	@GetMapping("/lines-for-pr/{id}")
	public JsonResponse listPrLineItems(@PathVariable int id) {
		JsonResponse jr = null;
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(id);
		if (lineItems.size() > 0) {
			jr = JsonResponse.getInstance(lineItems);
		} else {
			jr = JsonResponse.getErrorInstance("No Line Item found.");
		}
		return jr;
	}

	// get /list one item
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		Optional<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isPresent()) {
			jr = JsonResponse.getInstance(lineItem.get());
		} else {
			jr = JsonResponse.getErrorInstance("No Line Item found for id: " + id);
		}
		return jr;
	}

	// "create" method
	@PostMapping("/")
	public JsonResponse createLineItem(@RequestBody LineItem li) {
		JsonResponse jr = null;
		try {
			li = lineItemRepo.save(li);
			jr = JsonResponse.getInstance(li);
			recalculateTotal(li.getRequest());
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
	public JsonResponse updateLineItem(@RequestBody LineItem li) {
		JsonResponse jr = null;
		try {
			li = lineItemRepo.save(li);
			jr = JsonResponse.getInstance(li);
			recalculateTotal(li.getRequest());
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
			LineItem li = lineItemRepo.findById(id).get(); 
			lineItemRepo.deleteById(id);
			jr = JsonResponse.getInstance("Line Item id: " + id + " deleted successfully.");
			Request r = li.getRequest();
			recalculateTotal(r);
			} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Line Item: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}

	// method will (re)calculate the purchase request value and save it in the
	// request "total"
	public void recalculateTotal(Request request) {
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(request.getId());	
		// loop thru list (math performed in LineItem Entity) and sum to a total value
		double pRTotal= 0.0;
		for (LineItem lITotal: lineItems) {
			pRTotal += lITotal.getQuantity()*lITotal.getProduct().getPrice();
		}	
		request.setTotal(pRTotal);
		try {
			requestRepo.save(request);
		} catch (Exception e) {
			throw e;
		}	
			
	
	}
}
