import com.github.kennedyoliveira.pastebin4j.AccountCredentials;
import com.github.kennedyoliveira.pastebin4j.Paste;
import com.github.kennedyoliveira.pastebin4j.PasteBin;

import java.util.List;

/**
 * @author kennedy
 */
public class ListTrendsExample {

    public static void main(String[] args) {
        final String devKey = "366ff4c4897ee6d9a67900624a4836ef";

        final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey));

        final List<Paste> trends = pasteBin.listTrendingPastes();

        trends.forEach(System.out::println);
    }
}
