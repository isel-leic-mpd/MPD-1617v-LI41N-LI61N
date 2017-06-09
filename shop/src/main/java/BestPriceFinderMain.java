import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

import static benchmarking.MicroBenchmarking.measure;
import static java.util.stream.Collectors.toList;

public class BestPriceFinderMain {

    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    private static void singleShopContinuation() {
        Shop s = new Shop("The cheapest shop");


        CompletableFuture.supplyAsync(() -> s.getPrice("IStone"))
                    .thenAccept(System.out::println)
        ;
    }

    private static void singleShopAndDiscountSequential() {
        Shop s = new Shop("The cheapest shop");

        final String iStonePrice = s.getPrice("IStone");
        String[] priceStr = iStonePrice.split(":");
        Quote quote = createQuote(priceStr);
        final String priceWithDiscount = Discount.applyDiscount(quote);
        System.out.println(priceWithDiscount);
    }

    private static CompletableFuture<Void> singleShopAndDiscountWithContinuations() {
        Shop s = new Shop("The cheapest shop");

        Stream<Function<Quote, String>> discounts = Stream.of(
                Discount::applyDiscount,
                Discount::applyDiscount,
                Discount::applyDiscount,
                Discount::applyDiscount,
                Discount::applyDiscount,
                Discount::applyDiscount

        );

        return CompletableFuture.supplyAsync(() -> s.getPrice("IStone")) // CompletableFuture<String>
                .thenApply(price -> price.split(":"))                   // CompletableFuture<String[]>
                .thenApply(BestPriceFinderMain::createQuote)                  // CompletableFuture<Quote>
                .thenApply(q -> bestPriceWithDiscount(discounts, q))          // CompletableFuture<Stream<String>>
//                .thenApply(q -> discounts.map(d -> getPriceWithDiscount(d, q)))
//                .thenApply(doubleStream -> doubleStream.mapToDouble(Double::doubleValue).min().getAsDouble())
                .thenAccept(System.out::println);

    }

    private static double bestPriceWithDiscount(Stream<Function<Quote, String>> discounts, Quote quote) {
        return discounts.map(d -> CompletableFuture.supplyAsync(() -> d.apply(quote)))
                .collect(toList())
                .stream()
                .map(cfs -> cfs.join().split(":"))
                .map(strArr -> strArr[1])
                .mapToDouble(Double::parseDouble)
                .min()
                .getAsDouble();
    }

    private static double getPriceWithDiscount(Function<Quote, String> discount, Quote quote) {
        return Double.parseDouble(discount.apply(quote).split(":")[1]);
    }

    private static Quote createQuote(String[] priceStr) {
        return new Quote(priceStr[0], Double.parseDouble(priceStr[1]), Enum.valueOf(Discount.Code.class, priceStr[2]) );
    }


    public static void main(String[] args) throws IOException {
        //measure("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"), 1);
        //measure("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27S"), 1);
        //measure("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture("myPhone27S"), 1);
//        bestPriceFinder.printPricesStream("myPhone27S");


        //singleShopContinuation();
        //singleShopAndDiscountSequential();

        measure("singleShopAndDiscountWithContinuations", () -> singleShopAndDiscountWithContinuations().join(), 1);




    }
}
