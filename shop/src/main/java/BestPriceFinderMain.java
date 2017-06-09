import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static benchmarking.MicroBenchmarking.measure;

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
        Quote quote = new Quote(priceStr[0], Double.parseDouble(priceStr[1]), Enum.valueOf(Discount.Code.class, priceStr[2]) );
        final String priceWithDiscount = Discount.applyDiscount(quote);
        System.out.println(priceWithDiscount);


    }


    public static void main(String[] args) throws IOException {
        //singleShopContinuation();
        singleShopAndDiscountSequential();

        //measure("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"), 1);
        //measure("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27S"), 1);
        //measure("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture("myPhone27S"), 1);
//        bestPriceFinder.printPricesStream("myPhone27S");



    }
}
