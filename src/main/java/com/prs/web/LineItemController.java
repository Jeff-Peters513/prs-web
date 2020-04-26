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
		Optional<Request> requests = requestRepo.findById(id);
		List<LineItem> lineItems = lineItemRepo.findAllByRequest(requests);
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
			//call method recalculateTotal(id)
			//in this case the method removes the lineitem and adjusts total in Request assoicated with the lineitem id
		} catch (Exception e) {
			jr = JsonResponse.getErrorInstance("Error deleting Line Item: " + e.getMessage());
			e.printStackTrace();
		}
		return jr;
	}
	
//	public static double recalculateTotal(@PathVariable int id) {
//		//sum all active line Item totals of a PR and sets PRtotal?
//		double total= 0.0;
//		// load all Request lineItems and make change
//		//Optional<Request> requests = requestRepo.findById(id);
//		//List<LineItem> lineItems = lineItemRepo.findAllByRequest(requests);
//		//
//		//if requestId exist and has more than one lineItem then process: LineItem.Quanity * Product.Price for each lineitem in the request
//		if (!Request.getId(id) ==0) {
//			for (int i=0; i = arraysize; i++) {
//				double lineItemSubTotal = lineItem.quantity * product.price();
//				double pRTotal = lineItemSubTotal;
//				set.request.total(pRTotal);
//			}else {
//				System.out.println("No Purchase request exists no updated needed.");
//			}
//		}
//			
//		return total;
//		}
//	
	
	
}

