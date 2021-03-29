package co.com.sofka;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {
    private final PgPool client;

    public FruitResource(PgPool client) {
        this.client = client;
    }

    @GET
    public Multi<Fruit> get(){
        return Fruit.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(Long id){
        return Fruit.findById(client, id)
                .onItem().transform(fruit -> fruit != null ? Response.ok(fruit) : Response.status(Response.Status.NOT_FOUND))
                .onItem().transform(Response.ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(Fruit fruit){
        return fruit.save(client)
                .onItem().transform(id->URI.create("/fruits/" +id))
                .onItem().transform(uri->Response.created(uri).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(Long id, Fruit fruit){
        return fruit.update(client)
                .onItem().transform(updated->updated? Response.Status.OK : Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(Long id){
        return Fruit.delete(client, id)
                .onItem().transform(deleted-> deleted? Response.Status.NO_CONTENT: Response.Status.NOT_FOUND)
                .onItem().transform(status -> Response.status(status).build());
    }
}