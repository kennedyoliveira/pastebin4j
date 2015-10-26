import com.github.kennedyoliveira.pastebin4j.*;

/**
 * @author kennedy
 */
public class CreatePaste {

    public static void main(String[] args) {
        final String devKey = "366ff4c4897ee6d9a67900624a4836ef";
        final String userName = "KennedyOliviera";
        final String password = "jAfpYepS";

        final PasteBin pasteBin = new PasteBin(new AccountCredentials(devKey, userName, password));

//         Basic creation
//
        final Paste paste = new Paste();

        paste.setTitle("Testing API");
        paste.setExpiration(PasteExpiration.ONE_HOUR);
        paste.setHighLight(PasteHighLight.Java);
        paste.setVisibility(PasteVisibility.UNLISTED);
        paste.setContent("public class Teste { }");

        final String url = pasteBin.createPaste(paste);

        System.out.println("Paste created at url: " + url);

        // USing a builder
        final Paste pasteByBuilder = Paste.newBuilder()
                                          .withTitle("Testing API")
                                          .withExpiration(PasteExpiration.ONE_DAY)
                                          .withHighLight(PasteHighLight.Java)
                                          .withVisibility(PasteVisibility.PRIVATE)
                                          .withContent("public class Test2 {\n\npublic static void main(String[] args){}}")
                                          .build();

        final String url2 = pasteBin.createPaste(pasteByBuilder);

        System.out.println(url2);


        // Using a paste directly
        final String url3 = Paste.newBuilder()
                                 .withTitle("Testing API")
                                 .withExpiration(PasteExpiration.ONE_DAY)
                                 .withHighLight(PasteHighLight.Java)
                                 .withVisibility(PasteVisibility.PRIVATE)
                                 .withContent("public class Test2 {\n\npublic static void main(String[] args){}}")
                                 .build()
                                 .paste(pasteBin);// You can pass a pasteBIn instance or a Credentiaals paste(accountCredentials)

        System.out.println(url3);


        // Especial GuestPaste class, allow you to create pastes as guest event if
        // you specified a username and password in the account credentials
        // You can use all the same methods you can use in a normal paste on a guest paste, because
        // the guest paste is a sub class of Paste
        final GuestPaste guestPaste = new GuestPaste();

        guestPaste.setContent("Testing");
        guestPaste.setTitle("Testing");
        guestPaste.setExpiration(PasteExpiration.TEN_MINUTES);
        guestPaste.setVisibility(PasteVisibility.PRIVATE);

        final String guestUrl = pasteBin.createPaste(guestPaste);

        System.out.println(guestUrl);
    }
}
