import com.github.kennedyoliveira.pastebin4j.AccountCredentials;
import com.github.kennedyoliveira.pastebin4j.PasteBin;
import com.github.kennedyoliveira.pastebin4j.UserInformation;

/**
 * @author kennedy
 */
public class UserInformationExample {

    public static void main(String[] args) {
        final String devKey = "dev-key";
        final String userName = "user-name";
        final String password = "password";

        final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey, userName, password));

        final UserInformation userInformation = pasteBin.fetchUserInformation();

        System.out.println("Username: " + userInformation.getUsername());
        System.out.println("Account Type:" + userInformation.getAccountType());
        System.out.println("Default Paste Expiration: " + userInformation.getDefaultPasteExpiration().name());
        System.out.println("Default Paste Visibility: " + userInformation.getDefaultPasteVisility().name());
        System.out.println("User e-mail: " + userInformation.getEmail());
        System.out.println("User location: " + userInformation.getLocation());
        System.out.println("User avatar URL: " + userInformation.getUserAvatarUrl());
        System.out.println("User web site:" + userInformation.getWebsiteUrl());
    }
}
