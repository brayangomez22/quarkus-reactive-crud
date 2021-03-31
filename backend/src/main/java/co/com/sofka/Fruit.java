package co.com.sofka;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.stream.StreamSupport;

public class Fruit {
    public Long id;
    public String name;
    public Integer amount;

    public Fruit(){
    }

    public Fruit(String name){
        this.name=name;
    }

    public Fruit(Long id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public Fruit(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Fruit(Long id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Fruit(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public static Multi<Fruit> findAll(PgPool client){
        return client.query("SELECT id, name, amount FROM fruits").execute()
                .onItem().transformToMulti(set-> Multi.createFrom().items(()-> StreamSupport.stream(set.spliterator(), false)))
                .onItem().transform(Fruit::from);
    }

    public static Uni<Fruit> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT id, name, amount FROM fruits WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Uni<Long> save(PgPool client){
        return client.preparedQuery("INSERT INTO fruits (name, amount) VALUES ($1, $2) RETURNING (id)").execute(Tuple.of(name, amount))
                .onItem().transform(pgRowSet-> pgRowSet.iterator().next().getLong("id"));
    }

    public Uni<Boolean> update(PgPool client){
        return client.preparedQuery("UPDATE fruits SET name = $1, amount = $3 WHERE id = $2").execute(Tuple.of(name, id, amount))
                .onItem().transform(pgRowSet->pgRowSet.rowCount()==1);
    }

    public static Uni<Boolean> delete(PgPool client, Long id){
        return client.preparedQuery("DELETE FROM fruits WHERE id = $1").execute(Tuple.of(id))
                .onItem().transform(pgRowSet -> pgRowSet.rowCount()==1);
    }

    private static Fruit from(Row row) {
        return new Fruit(row.getLong("id"), row.getString("name"), row.getInteger("amount"));
    }
}
