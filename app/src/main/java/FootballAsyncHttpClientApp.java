import com.google.gson.Gson;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static java.util.stream.StreamSupport.stream;

/**
 * Copyright © 2014, Coriant and/or its affiliates. All rights reserved.
 * <p>
 * Coriant Java 8 training 2016 - Session 10 exercises
 *
 * @author Luís Falcão
 */

/**
 * Samle application that uses the <a href="http://unirest.io/java.html">Unirest</a> java HTTP library, with the
 * <a href="http://api.football-data.org/index">Football data REST api</a>
 */
public class FootballAsyncHttpClientApp {

    public static void main(String[] args) throws Exception {

        if(args.length == 0) args = new String[]{"439", "430", "436"};
    
//
//        System.out.println("############ Getting team leaders synchronous");
        Arrays
            .stream(args)
            //.map(App::getLeaderTeam)
            .map(id -> getLeaderTeam(id))
            .forEach(team -> {System.out.println(team); } );
            
//        System.out.println("############ Getting team leaders Async");
//        Arrays
//            .stream(args)
//            .forEach(id -> getLeaderTeamAsync(id, FootballAsyncHttpClientApp::printTeam));


//        final CompletableFuture<String> leaderLeague1 = getLeaderTeamAsync("439");
//        final CompletableFuture<String> leaderLeague2 = getLeaderTeamAsync("436");



//        leaderLeague1.thenAcceptAsync(FootballAsyncHttpClientApp::printTeam);
//
//
//        leaderLeague2.thenAcceptAsync(FootballAsyncHttpClientApp::printTeam);

        System.in.read();







    }

    private static void printTeam(String s) {
        System.out.println("api response processed on thread with id:" + Thread.currentThread().getId());
        System.out.println("thread is deamon:" + Thread.currentThread().isDaemon());
        System.out.println(s);
    }

    static void printTeam(Exception e, String teamName) { // Same idiomatic Node callback
        if(e != null) throw new RuntimeException(e);
        else {
            System.out.println(teamName);
            sleep(1000);
        }
    }
    
    static void sleep(int milis) {
        try{
             Thread.sleep(milis);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static final String url = "http://api.football-data.org/v1/soccerseasons/%s/leagueTable";
    private static Gson gson = new Gson();


    
    public static String getLeaderTeam(String leagueId) {


        final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
        try {
            System.out.println("getLeaderTeam thread id:" + Thread.currentThread().getId());
            return asyncHttpClient
                    .prepareGet(String.format(url, leagueId))
                    .execute()
                    .toCompletableFuture()
                    .thenApply(resp -> gson.fromJson(resp.getResponseBody(), Standings.class).standing[0].teamName)
                    .join();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }



//    public static void getLeaderTeamAsync(
//        String leagueId,
//        BiConsumer<Exception, String> callback)
//    {
//        try {
//            Unirest.get("http://api.football-data.org/v1/soccerseasons/{leagueId}/leagueTable")
//                .routeParam("leagueId", leagueId)
//
//                .asJsonAsync(new Callback<JsonNode>() {
//                    public void failed(UnirestException e) { callback.accept(e, null); }
//
//                    public void completed(HttpResponse<JsonNode> resp) {
//                        System.out.println("completed thread id:" + Thread.currentThread().getId());
//                        String leader = resp.getBody()
//                                            .getObject()
//                                            .getJSONArray("standing")
//                                            .getJSONObject(0)
//                                            .getString("teamName");
//                        callback.accept(null, leader);
//                    }
//
//                    public void cancelled() {
//                        callback.accept(new RuntimeException("The request has been cancelled"), null);
//                    }
//                });
//        } catch(Exception e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("getLeaderTeamAsync thread id:" + Thread.currentThread().getId());
//
//    }
//
//    public static CompletableFuture<String> getLeaderTeamAsync(String leagueId) {
//        CompletableFuture<String> cf = new CompletableFuture<>();
//        System.out.println("api request started on thread with id:" + Thread.currentThread().getId());
//        Unirest.get("http://api.football-data.org/v1/soccerseasons/{leagueId}/leagueTable")
//                .routeParam("leagueId", leagueId)
//
//                .asJsonAsync(new Callback<JsonNode>() {
//                    public void failed(UnirestException e) { cf.completeExceptionally(e); }
//
//                    public void completed(HttpResponse<JsonNode> resp) {
//
//                        String leader = resp.getBody()
//                                .getObject()
//                                .getJSONArray("standing")
//                                .getJSONObject(0)
//                                .getString("teamName");
//                        System.out.println("completed in thread with id:" + Thread.currentThread().getId());
//                        System.out.println("completed value is :" + leader);
//                        cf.complete(leader);
//
//                    }
//
//                    public void cancelled() {
//                        cf.cancel(true);
//                    }
//                });
//
//        System.out.println("getLeaderTeamAsync thread id:" + Thread.currentThread().getId());
//        return cf;
//    }
}


class Standings {
    public Standing[] standing;
}

class Standing {
    public String teamName;
}
