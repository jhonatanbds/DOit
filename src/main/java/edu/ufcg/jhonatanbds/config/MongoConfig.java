package edu.ufcg.jhonatanbds.config;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mongodb.*;
import edu.ufcg.jhonatanbds.converter.ToDoWriterConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(basePackages = "edu.ufcg.jhonatanbds.dao")
public class MongoConfig extends AbstractMongoConfiguration {

    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Override
    protected String getDatabaseName() {
        return "heroku_zv2trqp4";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(singletonList(new ServerAddress("ds153729.mlab.com", 53729)),
                singletonList(MongoCredential.createCredential("heroku_zv2trqp4", "heroku_zv2trqp4", "9dbsre2qaqf7a4q1fc3a5k8n58".toCharArray())));
    }

    @Override
    public String getMappingBasePackage() {
        return "edu.ufcg.jhonatanbds";
    }

    @Override
    public CustomConversions customConversions() {
        converters.add(new ToDoWriterConverter());
        return new CustomConversions(converters);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }
}
