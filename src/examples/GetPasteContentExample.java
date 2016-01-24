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

        if(pastes.isEmpty()) {
            System.out.println("No pastes to fetch the content :(");
            System.exit(0);
        }

        final Paste paste = paste.get(0);

        final String pasteContent = pasteBin.getPasteContent(paste);

        System.out.println(pasteContent);
    }
}
