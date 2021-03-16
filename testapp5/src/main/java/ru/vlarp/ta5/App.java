package ru.vlarp.ta5;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {
    private IgniteConfiguration igniteConfiguration;

    @Autowired
    public void setIgniteConfiguration(IgniteConfiguration igniteConfiguration) {
        this.igniteConfiguration = igniteConfiguration;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Ignite ignite = Ignition.start(igniteConfiguration);

        log.info("Hello there!");
        log.info("ignite.version={}", ignite.version());

        if (Arrays.asList(args).contains("-w")) {
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
            cache.put(1, "Hello");
            cache.put(2, "World!");
            cache.put(3, "0");
            log.info("write cache");
        }

        if (Arrays.asList(args).contains("-r")) {
            IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
            log.info("cache values {} {} {}", cache.get(1), cache.get(2), cache.get(3));
        }

        if (Arrays.asList(args).contains("-s")) {
            IgniteCompute compute = ignite.compute(ignite.cluster().forServers());
            compute.broadcast(new RemoteTask());
        }

        ignite.close();
    }

    public static class RemoteTask implements IgniteRunnable {
        @IgniteInstanceResource
        Ignite ignite;

        @Override
        public void run() {
            System.out.println("Hello node! id=" + ignite.cluster().localNode().id());
        }
    }

}