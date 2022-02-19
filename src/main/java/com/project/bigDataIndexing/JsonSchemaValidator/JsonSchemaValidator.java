package com.project.bigDataIndexing.JsonSchemaValidator;

import com.project.bigDataIndexing.ApiController.PlanController;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.validation.ValidationException;

public class JsonSchemaValidator {

    public void validateJsonSchema(JSONObject jsonObject) throws ValidationException{
        JSONObject jsonSchema = new JSONObject(new JSONTokener(PlanController.class.getResourceAsStream("/usecase.json")));
        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonObject);
    }
}
