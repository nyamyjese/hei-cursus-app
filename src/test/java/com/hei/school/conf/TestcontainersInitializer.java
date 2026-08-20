package com.hei.school.conf;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class TestcontainersInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:16-alpine")
          .withDatabaseName("hei_test")
          .withUsername("test")
          .withPassword("test");

  static {
    postgres.start();
  }

  @Override
  public void initialize(ConfigurableApplicationContext ctx) {
    TestPropertyValues.of(
            "spring.datasource.url=" + postgres.getJdbcUrl(),
            "spring.datasource.username=" + postgres.getUsername(),
            "spring.datasource.password=" + postgres.getPassword(),
            "spring.flyway.url=" + postgres.getJdbcUrl(),
            "spring.flyway.user=" + postgres.getUsername(),
            "spring.flyway.password=" + postgres.getPassword(),
            "aws.eventBridge.bus=dummy-test-bus",
            "aws.s3.bucket=dummy-test-bucket")
        .applyTo(ctx.getEnvironment());
  }
}
