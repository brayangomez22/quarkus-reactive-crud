package co.com.sofka;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;

public class DBInit {
    private final PgPool client;
    private final boolean schemaCreate;

    public DBInit(PgPool client,@ConfigProperty(name = "myapp.schema.create", defaultValue = "true") boolean schemaCreate) {
        this.client = client;
        this.schemaCreate = schemaCreate;
    }

    void onStart(@Observes StartupEvent ev){
        if(schemaCreate){
            initdb();
        }
    }

    private void initdb() {
        client.query("DROP TABLE IF EXISTS fruits").execute()
                .flatMap(r -> client.query("CREATE TABLE fruits (id SERIAL PRIMARY KEY, name TEXT NOT NULL)").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Papaya')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Aguacate')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Mandarina')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Limon')").execute())
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Pera')").execute())
                .await().indefinitely();
    }
}
