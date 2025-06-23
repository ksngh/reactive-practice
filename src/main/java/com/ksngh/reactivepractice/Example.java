package com.ksngh.reactivepractice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RestController
@Slf4j
public class Example {

    @GetMapping("/mono/01")
    public void helloReactivePractice() {
        Mono.just("Hello Reactor").subscribe(System.out::println);
    }

    @GetMapping("/flux")
    public void fluxReactivePractice() {
        Flux<String> sequence = Flux.just("Hello", "World");
        sequence.map(String::toUpperCase).subscribe(System.out::println);
    }

    @GetMapping("/mono/02")
    public void monoReactivePractice() {
        Mono.empty()
                .subscribe(
                none-> System.out.println("# emitted onNext signal"),
                error -> {},
                () -> System.out.println("# emitted onComplete")
        );
    }

    @GetMapping("/flux/02")
    public void fluxReactivePractice2() {
        Flux.just(6,9,13).map(num->num%2).subscribe(System.out::println);
    }

    @GetMapping("/flux/03")
    public void fluxReactivePractice3() {
        Flux.fromArray(new Integer[]{3,6,7,9})
                .filter(num -> num>6)
                .map(num->num*2)
                .subscribe(System.out::println);
    }

    @GetMapping("/flux/04")
    public void fluxReactivePractice4() {
        Flux<String> flux = Mono.justOrEmpty("Steve").concatWith(Mono.justOrEmpty("Jobs"));
        flux.subscribe(System.out::println);
    }

    @GetMapping("/flux/05")
    public void fluxReactivePractice5() {
        Flux.concat(
                Flux.just("Mercury","Venus","Earth"),
                Flux.just("Mars","Jupiter","Saturn"),
                Flux.just("Uranus","Neptune","Pluto")).collectList().subscribe(System.out::println);
    }

    @GetMapping("/flux/06")
    public void fluxReactivePractice6() throws InterruptedException {
        Flux<String> coldFlux =
                Flux
                        .fromIterable(Arrays.asList("KOREA","JAPAN","CHINA"))
                        .map(String::toLowerCase);
        coldFlux.subscribe(country -> log.info("# Subscriber1: {}", country));
        System.out.println("-------------------------");
        Thread.sleep(2000L);
        coldFlux.subscribe(country -> log.info("# Subscriber2: {}", country));
    }
}
