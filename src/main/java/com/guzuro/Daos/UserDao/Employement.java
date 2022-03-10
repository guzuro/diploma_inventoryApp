package com.guzuro.Daos.UserDao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonSerialize(using = EmployementObjectToJson.class)
@JsonDeserialize(using = JsonToEmployementObject.class)
public class Employement {

    private int id;
    private LocalDateTime employement_date;
    private double salary;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int user_id;

    public Employement() {

    }

    private Employement(LocalDateTime employement_date, double salary) {
        this.employement_date = employement_date;
        this.salary = salary;
    }

    public Employement(LocalDateTime employement_date, double salary, int user_id) {
        this.employement_date = employement_date;
        this.salary = salary;
        this.user_id = user_id;
    }

    public Employement(int id, LocalDateTime employement_date, double salary, int user_id) {
        this.id = id;
        this.employement_date = employement_date;
        this.salary = salary;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEmployement_date() {
        return employement_date;
    }

    public void setEmployement_date(LocalDateTime employement_date) {
        this.employement_date = employement_date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

class EmployementObjectToJson extends JsonSerializer<Employement> {
    @Override
    public void serialize(Employement employement, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", employement.getId());
        jsonGenerator.writeStringField("employement_date", employement.getEmployement_date().toString());
        jsonGenerator.writeNumberField("salary", employement.getSalary());
        jsonGenerator.writeNumberField("user_id", employement.getUser_id());
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
    }
}

class JsonToEmployementObject extends JsonDeserializer {

    @Override
    public Employement deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = 0;
        if (node.has("id")) {
            id = node.get("id").intValue();
        }
        double salary = node.get("salary").doubleValue();
        LocalDateTime ldt = LocalDateTime.parse(node.get("employement_date").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (node.has("id")) {
            int user_id  = node.get("user_id").intValue();
            return new Employement(id, ldt, salary, user_id);
        }
        return new Employement(ldt, salary, id);

    }
}
