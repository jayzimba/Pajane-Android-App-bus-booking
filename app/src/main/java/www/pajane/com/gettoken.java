package www.pajane.com;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class gettoken extends MainActivity{


    public String thetoken() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"amount\": \"3.0\",\r\n  \"currency\": \"EUR\",\r\n  \"externalId\": \"12131415\",\r\n  \"payer\": {\r\n    \"partyIdType\": \"MSISDN\",\r\n    \"partyId\": \"0963676321\"\r\n  },\r\n  \"payerMessage\": \"Pay for your booking on PajaneBooking\",\r\n  \"payeeNote\": \"string\"\r\n}");
        Request request = new Request.Builder()
                .url("https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay")
                .method("POST", body)
                .addHeader("X-Reference-Id", "6eb23d16-ec74-42f8-8687-82706eec1160")
                .addHeader("X-Target-Environment", "sandbox")
                .addHeader("Ocp-Apim-Subscription-Key", "c9ec5222f25749b29470b8e112ebd45d")
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSMjU2In0.eyJjbGllbnRJZCI6IjZlYjIzZDE2LWVjNzQtNDJmOC04Njg3LTgyNzA2ZWVjMTE2MCIsImV4cGlyZXMiOiIyMDIyLTA0LTI0VDEwOjUxOjQwLjMyOCIsInNlc3Npb25JZCI6IjFiYTk4ZjExLTExZjYtNGViMS1iNWY5LTJiNjllYjE4MDdiZiJ9.ASTYlAUq87pLfvZvtZMHBuPjGaOdUVqMaUhf22OZnXFRjoRpKB7yGlMInhH3GcbBJCg4ZqjfgVvldRCNwwFC-zNrWCok4hIHjLaNpdHNLejSSi-Ev-x3o2laLTwWz3mL2VQVK9Oi8Vb1oZK-V4shL49hMuknfOte1quB1Da2oUiEz2F7CtZcage7OxfH8nt-rciJGl7m8G06RfS43q-vnR3bQgbiu0t7XoRnPPaFOvMZxVW18icUmWI38plHbnOhUux-GwD9pokmHk1vzZ00zUANklo-dRw1CCKb-XgkC-d9wk8dgivZHHdyQCGs7RzwtimeirqwiPq4ijahm8qnaQ")
                .addHeader("Content-Type", "text/plain")
                .build();
        
        Response response = client.newCall(request).execute();

        String resp = response.body().toString();


        JSONObject object = new JSONObject(resp);

        String token = object.getString("access_token");
        String token_type = object.getString("token_type");
        String expires_in = object.getString("expires_in");
        String status = String.valueOf(response.code());

        System.out.println("TOKEN: "+ token);
        System.out.println("TOKEN_TYPE: "+ token_type);
        System.out.println("EXPIRES_IN: "+ expires_in);


        return token;
    }
}
