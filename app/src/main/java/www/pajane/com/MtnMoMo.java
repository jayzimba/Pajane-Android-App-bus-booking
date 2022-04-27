package www.pajane.com;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MtnMoMo {


    private final static String Ocp_Apim_Subscription_Key = "c9ec5222f25749b29470b8e112ebd45d";


     //method to get UUID
      public Response getUUID() throws IOException {
          OkHttpClient client = new OkHttpClient().newBuilder()
                  .build();
          Request request = new Request.Builder()
                  .url("https://www.uuidgenerator.net/api/version4")
                  .method("GET", null)
                  .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjZlY2VlMTgwLTlmNDQtNDNhNC1iMzI0LTI5MGYzMjk3ZmY3ZiIsImV4cGlyZXMiOiIyMDIyLTA0LTE3VDE5OjU3OjE2LjkwOSIsInNlc3Npb25JZCI6ImNkMjUyZGNjLWIwZjAtNGE1Ni1hODc5LWZhMGM5MjFkYzg0OCJ9.dM2uVv96hsx4MxUx6jpuXU6KzaFu85v4DI2IHAwSgkyNqlpQnv551XXujWIozf4RBtjy2rfU3pwfRpUaHNhdQGCUp-O-sjtPS7n45pILtCOLuuorCg5JHaNU6sdBEl4O-kh2jeSMdftDTQee_kCFq767wphXqAXpXUFuGZ32mnK9nAuDpcuwmuPYs8XDilk8qWbiq6bRyWdwg2BNoNE4ghSTyvFigpDbkhTiW9M1iSJh08ZDmqBehYeheHT_30c3frzn6j9wj1-skAGPs-B8tdnw4O4ypVYUspkZquB18VN8Kt1qpLWhFauFh42l8cmLZw6hfUh8FCwXrfD1vFHldw")
                  .build();
          Response response = client.newCall(request).execute();

          return response;
      }

    //create API user
    public Response createApiUser() throws IOException {
        String uuid = getUUID().toString();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"providerCallbackHost\": \"https://webhook.site/94d4854e-03de-4d90-9e8f-d89a3121d0c4\"\r\n}");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/v1_0/apiuser")
                .method("POST", body)
                .addHeader("X-Reference-Id", uuid)
                .addHeader("Ocp-Apim-Subscription-Key",Ocp_Apim_Subscription_Key )
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjZlY2VlMTgwLTlmNDQtNDNhNC1iMzI0LTI5MGYzMjk3ZmY3ZiIsImV4cGlyZXMiOiIyMDIyLTA0LTE3VDE5OjU3OjE2LjkwOSIsInNlc3Npb25JZCI6ImNkMjUyZGNjLWIwZjAtNGE1Ni1hODc5LWZhMGM5MjFkYzg0OCJ9.dM2uVv96hsx4MxUx6jpuXU6KzaFu85v4DI2IHAwSgkyNqlpQnv551XXujWIozf4RBtjy2rfU3pwfRpUaHNhdQGCUp-O-sjtPS7n45pILtCOLuuorCg5JHaNU6sdBEl4O-kh2jeSMdftDTQee_kCFq767wphXqAXpXUFuGZ32mnK9nAuDpcuwmuPYs8XDilk8qWbiq6bRyWdwg2BNoNE4ghSTyvFigpDbkhTiW9M1iSJh08ZDmqBehYeheHT_30c3frzn6j9wj1-skAGPs-B8tdnw4O4ypVYUspkZquB18VN8Kt1qpLWhFauFh42l8cmLZw6hfUh8FCwXrfD1vFHldw")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }


    //get created user
    public Response getCreatedUser(String uuid) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/v1_0/apiuser/"+uuid)
                .method("GET", null)
                .addHeader("Ocp-Apim-Subscription-Key", Ocp_Apim_Subscription_Key)
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjZlY2VlMTgwLTlmNDQtNDNhNC1iMzI0LTI5MGYzMjk3ZmY3ZiIsImV4cGlyZXMiOiIyMDIyLTA0LTE3VDE5OjU3OjE2LjkwOSIsInNlc3Npb25JZCI6ImNkMjUyZGNjLWIwZjAtNGE1Ni1hODc5LWZhMGM5MjFkYzg0OCJ9.dM2uVv96hsx4MxUx6jpuXU6KzaFu85v4DI2IHAwSgkyNqlpQnv551XXujWIozf4RBtjy2rfU3pwfRpUaHNhdQGCUp-O-sjtPS7n45pILtCOLuuorCg5JHaNU6sdBEl4O-kh2jeSMdftDTQee_kCFq767wphXqAXpXUFuGZ32mnK9nAuDpcuwmuPYs8XDilk8qWbiq6bRyWdwg2BNoNE4ghSTyvFigpDbkhTiW9M1iSJh08ZDmqBehYeheHT_30c3frzn6j9wj1-skAGPs-B8tdnw4O4ypVYUspkZquB18VN8Kt1qpLWhFauFh42l8cmLZw6hfUh8FCwXrfD1vFHldw")
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }

    //getAPIKEY

    public void getAPIKey(String uuid) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/v1_0/apiuser/"+ uuid+ "/apikey")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", Ocp_Apim_Subscription_Key)
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjZlY2VlMTgwLTlmNDQtNDNhNC1iMzI0LTI5MGYzMjk3ZmY3ZiIsImV4cGlyZXMiOiIyMDIyLTA0LTE3VDE5OjU3OjE2LjkwOSIsInNlc3Npb25JZCI6ImNkMjUyZGNjLWIwZjAtNGE1Ni1hODc5LWZhMGM5MjFkYzg0OCJ9.dM2uVv96hsx4MxUx6jpuXU6KzaFu85v4DI2IHAwSgkyNqlpQnv551XXujWIozf4RBtjy2rfU3pwfRpUaHNhdQGCUp-O-sjtPS7n45pILtCOLuuorCg5JHaNU6sdBEl4O-kh2jeSMdftDTQee_kCFq767wphXqAXpXUFuGZ32mnK9nAuDpcuwmuPYs8XDilk8qWbiq6bRyWdwg2BNoNE4ghSTyvFigpDbkhTiW9M1iSJh08ZDmqBehYeheHT_30c3frzn6j9wj1-skAGPs-B8tdnw4O4ypVYUspkZquB18VN8Kt1qpLWhFauFh42l8cmLZw6hfUh8FCwXrfD1vFHldw")
                .build();
        Response response = client.newCall(request).execute();
    }

    //generateToken

    public Response generateAPIToken() throws IOException {

          //requires username: uuid
        //and password: API Key

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/token/")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", Ocp_Apim_Subscription_Key)
                .addHeader("Authorization", "Basic NmVjZWUxODAtOWY0NC00M2E0LWIzMjQtMjkwZjMyOTdmZjdmOjcwNjdhZmQzNzI1ZDRjMTFiNDI5NjY3OTY4Y2YwMWIx")
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }

}
