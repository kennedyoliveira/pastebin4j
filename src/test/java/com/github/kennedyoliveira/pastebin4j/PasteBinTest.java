package com.github.kennedyoliveira.pastebin4j;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kennedy
 */
public class PasteBinTest {

    AccountCredentials accountCredentials;
    PasteBin pasteBin;
    List<Paste> createdPastes;

    @Before
    public void setUp() throws Exception {
        final String devkey = System.getProperty("pastebin4j.devkey");

        if (devkey == null)
            throw new RuntimeException("Dev key missing, especify the devkey in the running command with -Dpastebin4j.devkey=devkey");

        String username = System.getProperty("pastebin4j.username");

        if (username == null)
            throw new RuntimeException("Username missing, especify the username in the running command with -Dpastebin4j.username=username");

        String password = System.getProperty("pastebin4j.password");

        if (password == null)
            throw new RuntimeException("Password missing, especify the password in the running command with -Dpastebin4j.password=password");

        accountCredentials = new AccountCredentials(devkey, username, password);
        pasteBin = new PasteBin(accountCredentials);
        createdPastes = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        try {
            // delete all pastes
            if (pasteBin != null && !createdPastes.isEmpty())
                createdPastes.forEach(pasteBin::deletePaste);
        } catch (Exception e) {
            System.out.println("Error deleting the created pastes");
            e.printStackTrace();
        }
    }

    @Test
    public void testFetchUserInformation() throws Exception {
        final UserInformation userInformation = pasteBin.fetchUserInformation();

        assertThat(userInformation, is(notNullValue()));
        assertThat(userInformation.getUsername(), is(notNullValue()));
        assertThat(userInformation.getAccountType(), is(notNullValue()));
        assertThat(userInformation.getDefaultPasteExpiration(), is(notNullValue()));
        assertThat(userInformation.getDefaultPasteVisility(), is(notNullValue()));
        assertThat(userInformation, is(pasteBin.getUserInformation().get()));
    }

    @Test
    public void testCreatePaste() throws Exception {
        final Paste paste = Paste.newBuilder()
                                 .withTitle("pastebin4j")
                                 .withContent(UUID.randomUUID().toString())
                                 .withVisibility(PasteVisibility.PUBLIC)
                                 .withHighLight(PasteHighLight.TEXT)
                                 .withExpiration(PasteExpiration.ONE_MONTH)
                                 .build();

        final String url = pasteBin.createPaste(paste);

        createdPastes.add(paste);

        assertThat(url, containsString("pastebin.com/"));
        assertThat(paste.getUrl(), is(equalTo(url)));
        assertThat(paste.getKey(), is(notNullValue()));
    }

    @Test
    public void testCreatePasteUsingThePasteItself() throws Exception {
        final Paste paste = Paste.newBuilder()
                                 .withTitle("pastebin4j")
                                 .withContent(UUID.randomUUID().toString())
                                 .withVisibility(PasteVisibility.PUBLIC)
                                 .withHighLight(PasteHighLight.TEXT)
                                 .withExpiration(PasteExpiration.ONE_MONTH)
                                 .build();

        final String url = paste.paste(pasteBin);

        createdPastes.add(paste);

        assertThat(url, containsString("pastebin.com/"));
        assertThat(paste.getUrl(), is(equalTo(url)));
        assertThat(paste.getKey(), is(notNullValue()));
    }

    @Test
    public void testListUserPastes() throws Exception {
        testCreatePaste();

        final List<Paste> userPastes = pasteBin.listUserPastes();

        assertThat(userPastes, allOf(notNullValue(), hasSize(greaterThan(0))));
    }

    @Test
    public void testDeletePaste() throws Exception {
        final Paste createdPaste = Paste.newBuilder()
                                        .withTitle("pastebin4j " + UUID.randomUUID().toString())
                                        .withContent(UUID.randomUUID().toString())
                                        .withVisibility(PasteVisibility.PUBLIC)
                                        .withHighLight(PasteHighLight.TEXT)
                                        .withExpiration(PasteExpiration.ONE_MONTH)
                                        .build();

        pasteBin.createPaste(createdPaste);

        final String content = createdPaste.getContent();

        final List<Paste> pastes = pasteBin.listUserPastes();

        final Optional<Paste> paste = pastes.stream().filter(p -> p.fetchContent().equals(content)).findFirst();

        assertThat("Found the paste created", paste.isPresent(), is(true));

        final Paste pasteFound = paste.get();

        final boolean deleted = pasteBin.deletePaste(pasteFound);

        assertThat(deleted, is(true));

        final List<Paste> newUserPastes = pasteBin.listUserPastes();

        boolean foundPasteDeleted = newUserPastes.stream().anyMatch(p -> p.getKey().equals(pasteFound.getKey()));

        assertThat(foundPasteDeleted, is(false));
    }

    @Test
    public void testListTrendingPastes() throws Exception {
        final List<Paste> pastes = pasteBin.listTrendingPastes();

        // assertThat(pastes, allOf(notNullValue(), hasSize(18))); // API should return 18 acording to docs, but this is not the case :(
        assertThat(pastes, is(notNullValue()));
        assertThat(pastes.size(), greaterThan(0));
    }

    @Test
    public void testGetPasteContent() throws Exception {
        final Paste paste = new Paste("rrPV9pzZ");

        // New way to fetch content
        final String contents = pasteBin.getPasteContent(paste);

        assertThat(contents, is("test for pastebin4j api"));

        // Old way to fetch content
        assertThat(paste.fetchContent(), is("test for pastebin4j api"));
    }

    @Test
    public void testGetPrivatePasteContent() throws Exception {
        String content = UUID.randomUUID().toString();

        final Paste paste = Paste.newBuilder()
                                 .withTitle("Private paste test " + UUID.randomUUID().toString())
                                 .withContent(content)
                                 .withExpiration(PasteExpiration.TEN_MINUTES)
                                 .withHighLight(PasteHighLight.TEXT)
                                 .withVisibility(PasteVisibility.PRIVATE)
                                 .build();

        pasteBin.createPaste(paste);

        createdPastes.add(paste);

        final String pasteContent = pasteBin.getPasteContent(paste);

        assertThat(pasteContent, is(equalTo(content)));
    }

    @Test
    public void createGuestPaste() throws Exception {
        final String content = UUID.randomUUID().toString();
        final String title = "PasteBin4j test -> " + LocalDateTime.now().toString();

        final GuestPaste guestPaste = new GuestPaste();
        guestPaste.setContent(content);
        guestPaste.setTitle(title);
        guestPaste.setExpiration(PasteExpiration.TEN_MINUTES);
        guestPaste.setVisibility(PasteVisibility.PUBLIC);
        guestPaste.setHighLight(PasteHighLight.TEXT);
        guestPaste.paste(new AccountCredentials(System.getProperty("pastebin4j.devkey")));

        assertThat(guestPaste.getUrl(), is(allOf(notNullValue(), containsString("pastebin.com/"))));

        // checks if the paste was created for my user, shouldn't be
        final boolean pasteCreatedAsUserPaste = pasteBin.listUserPastes().stream().noneMatch(p -> title.equalsIgnoreCase(p.getTitle()));

        assertThat(pasteCreatedAsUserPaste, is(true));
    }

    @Test
    public void testPasteToString() throws Exception {
        final Paste paste = Paste.newBuilder()
                                 .withTitle("pastebin4j")
                                 .withContent(UUID.randomUUID().toString())
                                 .withVisibility(PasteVisibility.PUBLIC)
                                 .withHighLight(PasteHighLight.TEXT)
                                 .withExpiration(PasteExpiration.ONE_MONTH)
                                 .build();

        final String pasteInfo = paste.toString();

        assertThat(pasteInfo, is(allOf(notNullValue(), containsString("key="), containsString("Paste{"), containsString("url"))));
    }

    @Test
    public void testCreatePasteWithConstructor() throws Exception {
        final String content = UUID.randomUUID().toString();
        final String title = "PasteBin4j - " + LocalDateTime.now().toString();

        final Paste paste = new Paste(title, content, PasteHighLight.TEXT, PasteExpiration.TEN_MINUTES, PasteVisibility.UNLISTED);
        final String url = paste.paste(accountCredentials);

        createdPastes.add(paste);

        assertThat(url, containsString("pastebin.com/"));
        assertThat(paste.getUrl(), is(equalTo(url)));
        assertThat(paste.getKey(), is(notNullValue()));
    }

    @Test
    public void test_get_account_credentials() throws Exception {
        assertThat(pasteBin.getAccountCredentials(), is(this.accountCredentials));
    }

    @Test
    public void testBuilder() throws Exception {
        final Paste paste = Paste.newBuilder()
                                 .withContent("content")
                                 .withTitle("title")
                                 .withVisibility(PasteVisibility.PRIVATE)
                                 .withHighLight(PasteHighLight.Java)
                                 .withExpiration(PasteExpiration.ONE_MONTH)
                                 .build();

        assertThat(paste.getTitle(), is("title"));

        final Paste copyPaste = Paste.newBuilder(paste)
                                     .withTitle("Another title")
                                     .build();

        assertThat(copyPaste.getTitle(), is("Another title"));
        assertThat(copyPaste.getContent(), is("content"));
        assertThat(copyPaste.getVisibility(), is(PasteVisibility.PRIVATE));
        assertThat(copyPaste.getHighLight(), is(PasteHighLight.Java));
        assertThat(copyPaste.getExpiration(), is(PasteExpiration.ONE_MONTH));
    }

    @Test
    public void testListUserPastesWithLimit() throws Exception {
        @NotNull final List<Paste> userPastes = pasteBin.listUserPastes(1);

        assertThat(userPastes, hasSize(1));
    }
}