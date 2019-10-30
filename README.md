## Simple project to play with Java Futures

### Multi-threading

#### Example 1 - Spring @Async

Main classes: `Application` and `ServiceTaxes` 
https://github.com/simonmontanez/playing-futures/blob/master/java/src/main/java/com/company/Application.java#L15


Using Spring @Async the `ServiceTaxes.getDiscount()` and  `ServiceTaxes.getTaxes()` methods return
a `CompletableFuture` each one running in a different thread pool. Thus, the functions executed inside 
the `thenAccept` method are handled in its each own thread.

This first example is demonstrating that behavior. When the functions are called some thread info is logged.

Let's see the output of `gradle bootRun`:

 
```
Running in main thread. Thread: Name:main Id:1
Getting discount. Thread: Name:ThreadPool-ServiceDiscount-1 Id:16
Getting taxes. Thread: Name:ThreadPool-ServiceTaxes-1 Id:17

Setting final price given taxes. Thread: Name:ThreadPool-ServiceTaxes-1 Id:17
Setting final price given discount. Thread: Name:ThreadPool-ServiceDiscount-1 Id:16

```
 
As we can see, first we have the main thread pool where the `Amount` is initialized. Then the calls `this.serviceTaxes.getDiscount()` 
and `this.serviceTaxes.getTaxes().thenAccept` are executed in the threads pools `ThreadPool-ServiceDiscount` and `ThreadPool-ServiceTaxes` each one with its own thread id.

When the Completable Futures have a result the functions inside `thenAccept` are executed and we can see in the logs to which thread pool it belongs.

In this example, `amount.finalPrice` is deliberately updated in different threads in order to demonstrate
that it's unsafe to modify the state of an object through multiple threads. 
Each execution will independently update the field value depending on the time consumed by the Future. 
So, we can't trust that at the end of the flow the amount will have the expected state.

