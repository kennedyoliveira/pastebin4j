import com.github.kennedyoliveira.pastebin4j.AccountCredentials;
import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.PasteBin;
import com.github.kennedyoliveira.pastebin4j.PasteVisibility;

import java.util.List;
import java.util.Optional;

/**
 * @author kennedy
 */
public class GetPasteContentExample {

    public static void main(String[] args) {
        final String devKey = "dev-key";
        final String userName = "user-name";
        final String password = "password";

        final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey, userName, password));

        final List<Paste> pastes = pasteBin.listUserPastes();

        // Currently implementation doens't support fetching
        // private paste contents :( i'll work on that!
        final Optional<Paste> paste = pastes.stream()
                                            .filter(p -> p.getVisibility() != PasteVisibility.PRIVATE)
                                            .findFirst();

        if (!paste.isPresent()) {
            System.out.println("You doesn't have any paste that isn't private :(");
            return;
        }

        final String pasteContent = paste.get().fetchContent();

        System.out.println(pasteContent);
    }
}
