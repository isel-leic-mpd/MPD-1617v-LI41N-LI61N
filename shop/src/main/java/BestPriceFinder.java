import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                                                   new Shop("LetsSaveBig"),
                                                   new Shop("MyFavoriteShop"),
                                                   new Shop("BuyItAll"),
                                                    new Shop("ShopEasy")
    );

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });


    public List<String> findPricesSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
//                .map(Quote::parse)
//                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
//                .map(Quote::parse)
//                .map(Discount::applyDiscount)
                .collect(toList());
    }

    public List<String> findPricesFuture(String product) {
//        final Stream<CompletableFuture<String>> pricesStream =
//                findPricesStream(product);
//
//
//
//
//        final List<CompletableFuture<String>> pricesCfStream =
//                pricesStream  // Stream<CompletableFuture<String>>>
//                    .collect(toList());
//        return pricesCfStream.stream()
//                .map(CompletableFuture::join);

//      return findPricesStream(product)
//              .map(cf -> cf.join())      // Stream<String>
//              .collect(toList());

        List<CompletableFuture<String>> priceFutures =
             findPricesStream(product)
                .collect(toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    private Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
//                .map(future -> future.thenApply(Quote::parse))
//                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
//        ;

//        return shops.stream()
//                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
//                //.map(future -> future.thenApply(Quote::parse))
//                //.map(future -> future.thenApplyAsync(quote -> Discount.applyDiscountAsync(quote).join(), executor))
                ;
    }

    private Stream<CompletableFuture<Quote>> findPricesStreamQuote(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(q -> applyDiscount(q)))
                //.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
        ;

//        return shops.stream()
//                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
//                //.map(future -> future.thenApply(Quote::parse))
//                //.map(future -> future.thenApplyAsync(quote -> Discount.applyDiscountAsync(quote).join(), executor))
//                ;
    }
    
    
    private CompletableFuture<Quote> applyDiscount(Quote q) {
        final CompletableFuture<Double> doubleCompletableFuture = Discount.applyDiscountAsync(q);

        return doubleCompletableFuture.thenApply(d -> { q.setPriceWithDiscount(d); return q; });



    }

    public void printPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture<Void>[] futures = findPricesStream(product) // Stream<CompletableFuture<String>>
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))  // Stream<CompletableFuture<Void>>
                .toArray((IntFunction<CompletableFuture<Void>[]>) (CompletableFuture[]::new));

        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

}
