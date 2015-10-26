import com.github.kennedyoliveira.pastebin4j.AccountCredentials;
import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.PasteBin;

import java.util.List;

/**
 * @author kennedy
 */
public class ListUserPasteExample {

    public static void main(String[] args) {
        final String devKey = "dev-key";
        final String userName = "user-name";
        final String password = "password";

        final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey, userName, password));

        // This methods never return null.
        final List<Paste> pastes = pasteBin.listUserPastes();

        if (pastes.isEmpty()) {
            System.out.println("You don't have any pastes");
            return;
        }

        final Paste paste = pastes.get(0);

        System.out.println("Title: " + paste.getTitle());
        System.out.println("Visibility: " + paste.getVisibility().name());
        System.out.println("Unique Key: " + paste.getKey());
        System.out.println("Sintax Highlight: " + paste.getHighLight().name());
        System.out.println("Paste Date: " + paste.getLocalPasteDate());
        System.out.println("Paste Expiration: " + paste.getExpiration());
        System.out.println("Paste Expiration Date: " + paste.getLocalExpirationDate());
        System.out.println("Hits: " + paste.getHits());
        System.out.println("URL:  " + paste.getUrl());
        System.out.println("Size: " + paste.getSize());

        // Prints all information of all pastes
        pastes.forEach(System.out::println);
    }
}
