package co.com.sofka;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.internal.mapping.GsonMapper;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FruitResourceTest {
    @Test
    void getAllFruits(){
        given().when().get("/fruits")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":2,\"name\":\"Aguacate\"},{\"id\":4,\"name\":\"Limon\"},{\"id\":3,\"name\":\"Mandarina\"}]"));
    }


    @Test
    void getSingleFruit(){
        Long id = 2L;
        given().pathParam("id", id)
                .when().get("/fruits/{id}")
                .then()
                    .statusCode(200)
                    .body(is("{\"id\":2,\"name\":\"Pera\"}"));
    }

    @Test
    void createFruit(){
        Fruit pear = new Fruit("Pera");
        given()
                .body(pear)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/fruits")
                .then()
                    .statusCode(201);
    }

    @Test
    void updateFruit(){
        Long id = 2L;
        Fruit fruit = new Fruit(id,"Pera");
        given().pathParam("id", id)
                .body(fruit)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when().put("/fruits/{id}")
                .then()
                    .statusCode(200);
    }

    @Test
    void deleteFruit(){
        Long id = 1L;
        given().pathParam("id", id)
                .when().delete("/fruits/{id}")
                .then()
                .statusCode(204);
    }
}