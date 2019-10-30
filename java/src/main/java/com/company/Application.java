package com.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.currentThread;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    private final ServiceTaxes serviceTaxes;

    @Autowired
    public Application(ServiceTaxes serviceTaxes) {
        this.serviceTaxes = serviceTaxes;
    }

    public static void main(String[] args) {
        new SpringApplication(Application.class).run();
    }

    @Override
    public void run(String[] args) throws InterruptedException {
        exampleRunningFutures();
        log.info("Done");
        System.exit(0);
    }

    public void exampleRunningFutures() throws InterruptedException {

        log.info("Running in main thread. Thread: {}",  threadInfo());

        Amount amount = new Amount();

        amount.setFinalPrice(100d);

        CompletableFuture<Void> futureDiscount  = this.serviceTaxes.getDiscount().thenAccept(
                x ->{
                    log.info("Setting final price given discount. Thread: {}",  threadInfo());
                    amount.setFinalPrice(amount.getFinalPrice() - x);
                }
        );

        CompletableFuture<Void> futureTaxes  = this.serviceTaxes.getTaxes().thenAccept(x ->{
                    log.info("Setting final price given taxes. Thread: {}",  threadInfo());
                    amount.setFinalPrice( amount.getFinalPrice() + (amount.getFinalPrice() * x));
                }
        );

        futureDiscount.join();
        futureTaxes.join();

        log.info("Object  amount {} ", amount );
    }

    private String threadInfo(){
        return "Name:" + currentThread().getName() + " Id:"  + currentThread().getId();
    }
}


