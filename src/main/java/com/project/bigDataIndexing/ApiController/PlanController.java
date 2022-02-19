package com.project.bigDataIndexing.ApiController;
import com.project.bigDataIndexing.ApiService.ApiService;
import com.project.bigDataIndexing.JsonSchemaValidator.JsonSchemaValidator;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonObject;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController

public class PlanController {


    ApiService service = new ApiService();
    JsonSchemaValidator schemaValidator = new JsonSchemaValidator();

    @RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,value = "/plan")
    public ResponseEntity createPlan(@RequestBody String data) throws URISyntaxException {

        if(data == null || data.isEmpty()){
            return ResponseEntity.badRequest().body(new JSONObject().put("message","Input is not valid").toString());
        }

        JSONObject JSONdata = new JSONObject(data);

        try{
            schemaValidator.validateJsonSchema(JSONdata);
        } catch (ValidationException e) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JSONObject().put("message","Invalid Json Schema").toString());
        }

        if(service.ifObjectExists((String) JSONdata.get("objectId"))){
            System.out.println("in create plan already exist...");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new JSONObject().put("message","Plan already exist").toString());
        }

        String result = service.createPlan(JSONdata);
        System.out.println("in create plan:"+result);
        JSONObject res1 = new JSONObject();
        res1.put("objectId", result);
        res1.put("message","created plan");
        return ResponseEntity.created(new URI("/plan/" +JSONdata.get("objectId").toString())).body(res1.toString());
    }

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE,value = "/plan/{objectId}")
    public ResponseEntity getPlan(@PathVariable String objectId){
        System.out.println("inside get method");
        if(!service.ifObjectExists(objectId)){
            System.out.println("in get object not exists...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JSONObject().put("message","Plan does not exist").toString());
        }
        JSONObject planObj = service.getPlan(objectId);
        System.out.println("returning from get");
        return ResponseEntity.ok().body(planObj.toString());
    }

    @RequestMapping(method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE,value = "/plan/{keyToDelete}")
    public ResponseEntity deletePlan(@PathVariable String keyToDelete){
        System.out.println("in delete");
        if(!service.ifObjectExists(keyToDelete)){
            System.out.println("in delete object not exists...");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JSONObject().put("message","No object to delete").toString());
        }
        service.deletePlan(keyToDelete);
        return ResponseEntity.noContent().build();
    }
}
