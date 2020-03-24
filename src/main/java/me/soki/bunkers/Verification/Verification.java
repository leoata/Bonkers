package me.soki.bunkers.Verification;

import com.labs64.netlicensing.domain.vo.Context;
import com.labs64.netlicensing.domain.vo.SecurityMode;
import com.labs64.netlicensing.domain.vo.ValidationParameters;
import com.labs64.netlicensing.domain.vo.ValidationResult;
import com.labs64.netlicensing.exception.NetLicensingException;
import com.labs64.netlicensing.service.LicenseeService;
import me.soki.bunkers.Main.Main;
import me.soki.bunkers.Main.OS;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Verification {

    private final static int CODE_OK = 0;
    private final static int CODE_ERROR = 1;

    private static final String randomLicenseeSecret = UUID.randomUUID().toString();
    private static String HWID;

    static {
        try {
            HWID = Main.OPERATING_SYSTEM == OS.LINUX ? Hardware4Nix.getSerialNumber() : Hardware4Win.getSerialNumber(Main.DRIVE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String API_KEY = getApiKey(HWID);

    public static void main(String[] args) {
        new Verification();
    }
    public Verification() {

        final Context context = new Context();



        final String PRODUCT_NUMBER = "PDSHMKAAX";
        final String PRODUCT_MODULE_NUMBER = "MPDIUJ4I5";
        final String LICENSEE_NUMBER = "IFAIE3RQV";
        final String LICENSE_NUMBER = "LA9GWSFS6";
        final String LICENSEE_NAME = "sokiii";


        int exitCode = CODE_OK;

        try {

            // region ********* Validate

            String hwid = Main.OPERATING_SYSTEM == OS.LINUX ? Hardware4Nix.getSerialNumber() : Hardware4Win.getSerialNumber(Main.DRIVE);
            System.out.println(hwid);
            Bukkit.getServer().broadcastMessage(hwid);

            final ValidationParameters validationParameters = new ValidationParameters();
            validationParameters.put(PRODUCT_MODULE_NUMBER, "nodeSecret", hwid);
            validationParameters.setLicenseeSecret(randomLicenseeSecret);
            validationParameters.setLicenseeName(LICENSEE_NAME);
            validationParameters.setProductNumber(PRODUCT_NUMBER);

            ValidationResult validationResult = null;

            // Validate using APIKey
            context.setBaseUrl("https://go.netlicensing.io/core/v2/rest");
            context.setSecurityMode(SecurityMode.APIKEY_IDENTIFICATION);
            context.setApiKey(API_KEY);
            validationResult = LicenseeService.validate(context, LICENSEE_NUMBER, validationParameters);
            System.out.println("Validation result (APIKey) :" + validationResult);

            // endregion

            System.out.println("All done.");

        } catch (final NetLicensingException e) {
            System.out.println("Got NetLicensing exception :" + e);
            e.printStackTrace();
            exitCode = CODE_ERROR;
        } catch (final Exception e) {
            System.out.println("Got exception :" + e);
            e.printStackTrace();
            exitCode = CODE_ERROR;
        }
        if (exitCode == CODE_ERROR) {
            //System.exit(exitCode);
        }
        
    }
    private static String loadFileContent(final String fileName) throws IOException {
        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final InputStream inputStream = classloader.getResourceAsStream(fileName);
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

    public static String getApiKey(String hwid) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://leo.software/rest/bunkers/getapikey/"+ hwid).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 401) {
                // ... handle failed request
                return null;
            }
            return response.body().string();
            // ... do something with response
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
