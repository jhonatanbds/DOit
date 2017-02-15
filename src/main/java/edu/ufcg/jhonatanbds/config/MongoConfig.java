package edu.ufcg.jhonatanbds.config;

import java.util.ArrayList;
import java.util.List;

import edu.ufcg.jhonatanbds.converter.ToDoWriterConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "edu.ufcg.jhonatanbds.dao")
public class MongoConfig extends AbstractMongoConfiguration {

    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Override
    protected String getDatabaseName() {
        return "heroku_zv2trqp4";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("ds153729.mlab.com", 53729);
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
