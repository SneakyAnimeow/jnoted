package club.anims.jnoted;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
@Slf4j
public class JnotedApplication {
    private final Environment env;

    /**
     * This is the constructor of the application.
     * @param env Environment
     */
    public JnotedApplication(Environment env) {
        this.env = env;
    }

    /**
     * This is the main method of the application.
     * @param args Arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(JnotedApplication.class, args);
    }

    /**
     * This bean is used to connect to the database.
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }
}
