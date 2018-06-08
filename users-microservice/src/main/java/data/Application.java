package data;

import data.domain.nodes.User;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import data.repositories.UserRepository;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@SpringBootApplication
@EnableNeo4jRepositories("data.repositories")
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
public class Application  {
   
    @Autowired UserRepository userRep;
    
    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError"
//            ,
//         commandProperties = {
//                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
//                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
//                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
//                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "180000") }, 
//         threadPoolProperties = {
//                @HystrixProperty(name = "coreSize", value = "30"),
//                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "180000") }
      )
    public String home(@RequestParam String name) {
        User user = new User();
        user.setFirstName("aaa");
        user.setLastName("bbb");
        
        user = userRep.save(user);
        
         System.out.println(user.getFirstName());
        
        return "hi "+userRep.findAll().size();
    }

    public String hiError(String name, Throwable e) {
        e.printStackTrace();
        return "hi,"+name+",sorry,error!";
    }


    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(new File("target/test2.db"));
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        RepositoryRestConfiguration restConfiguration = ctx.getBean( RepositoryRestConfiguration.class);
        restConfiguration.exposeIdsFor(User.class);
    }

    @Bean
    public ResourceProcessor<Resource<User>> movieProcessor() {
        return new ResourceProcessor<Resource<User>>() {
            @Override
            public Resource<User> process(Resource<User> resource) {

                resource.add(new Link("/movie/movies", "movies"));
                return resource;
            }
        };
    }
}
