package com.github.kennedyoliveira.pastebin4j.api;

import com.github.kennedyoliveira.pastebin4j.*;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kennedy
 */
public class XMLUtilsTest {

    @Test
    public void testUnMarshal_Single_Paste() throws Exception {
        String xml = "<paste>\n" +
                "<paste_key>aismmqn8</paste_key>\n" +
                "<paste_date>1445820681</paste_date>\n" +
                "<paste_title>Testing API</paste_title>\n" +
                "<paste_size>22</paste_size>\n" +
                "<paste_expire_date>1445824281</paste_expire_date>\n" +
                "<paste_private>1</paste_private>\n" +
                "<paste_format_long>Java</paste_format_long>\n" +
                "<paste_format_short>java</paste_format_short>\n" +
                "<paste_url>http://pastebin.com/aismmqn8</paste_url>\n" +
                "<paste_hits>1</paste_hits>\n" +
                "</paste>";

        final Paste paste = XMLUtils.unMarshal(xml, Paste.class);

        assertThat(paste, notNullValue());
        assertThat(paste.getKey(), is("aismmqn8"));
        assertThat(paste.getTitle(), is("Testing API"));
        assertThat(paste.getSize(), is(22L));
        assertThat(paste.getHighLight(), is(PasteHighLight.Java));
        assertThat(paste.getUrl(), is("http://pastebin.com/aismmqn8"));
        assertThat(paste.getHits(), is(1L));
    }

    @Test
    public void test_unmarshal_multiple_pastes() throws Exception {
        String xml = "<paste>\n" +
                "<paste_key>aismmqn8</paste_key>\n" +
                "<paste_date>1445820681</paste_date>\n" +
                "<paste_title>Testing API</paste_title>\n" +
                "<paste_size>22</paste_size>\n" +
                "<paste_expire_date>1445824281</paste_expire_date>\n" +
                "<paste_private>1</paste_private>\n" +
                "<paste_format_long>Java</paste_format_long>\n" +
                "<paste_format_short>java</paste_format_short>\n" +
                "<paste_url>http://pastebin.com/aismmqn8</paste_url>\n" +
                "<paste_hits>1</paste_hits>\n" +
                "</paste>\n" +
                "<paste>\n" +
                "<paste_key>LwZmBx0n</paste_key>\n" +
                "<paste_date>1432654741</paste_date>\n" +
                "<paste_title>Download via power shell</paste_title>\n" +
                "<paste_size>167</paste_size>\n" +
                "<paste_expire_date>0</paste_expire_date>\n" +
                "<paste_private>1</paste_private>\n" +
                "<paste_format_long>Power Shell</paste_format_long>\n" +
                "<paste_format_short>powershell</paste_format_short>\n" +
                "<paste_url>http://pastebin.com/LwZmBx0n</paste_url>\n" +
                "<paste_hits>4</paste_hits>\n" +
                "</paste>\n" +
                "<paste>\n" +
                "<paste_key>F5vujSrj</paste_key>\n" +
                "<paste_date>1432088041</paste_date>\n" +
                "<paste_title>Insert Mailing Template</paste_title>\n" +
                "<paste_size>899</paste_size>\n" +
                "<paste_expire_date>0</paste_expire_date>\n" +
                "<paste_private>1</paste_private>\n" +
                "<paste_format_long>SQL</paste_format_long>\n" +
                "<paste_format_short>sql</paste_format_short>\n" +
                "<paste_url>http://pastebin.com/F5vujSrj</paste_url>\n" +
                "<paste_hits>4</paste_hits>\n" +
                "</paste>";

        final Pastes pastes = XMLUtils.unMarshal(String.format("<pastes>%s</pastes>", xml), Pastes.class);

        assertThat(pastes, is(notNullValue()));
        assertThat(pastes.getPastes(), hasSize(3));
        assertThat(pastes.getPastes().get(0).getKey(), is("aismmqn8"));
    }

    @Test
    public void test_unmarshal_user_information() throws Exception {
        String xml = "<user>\n" +
                "\t<user_name>wiz_kitty</user_name>\n" +
                "\t<user_format_short>text</user_format_short>\n" +
                "\t<user_expiration>N</user_expiration>\n" +
                "\t<user_avatar_url>http://pastebin.com/cache/a/1.jpg</user_avatar_url>\n" +
                "\t<user_private>1</user_private>\n" +
                "\t<user_website>http://myawesomesite.com</user_website>\n" +
                "\t<user_email>oh@dear.com</user_email>\n" +
                "\t<user_location>New York</user_location>\n" +
                "\t<user_account_type>1</user_account_type> (0 normal, 1 PRO)\n" +
                "</user>";

        final UserInformation userInformation = XMLUtils.unMarshal(xml, UserInformation.class);

        assertThat(userInformation.getUsername(), is("wiz_kitty"));
        assertThat(userInformation.getAccountType(), is(AccountType.PRO));
        assertThat(userInformation.getDefaultPasteExpiration(), is(PasteExpiration.NEVER));
        assertThat(userInformation.getUserAvatarUrl(), is("http://pastebin.com/cache/a/1.jpg"));
        assertThat(userInformation.getWebsiteUrl(), is("http://myawesomesite.com"));
        assertThat(userInformation.getEmail(), is("oh@dear.com"));
        assertThat(userInformation.getLocation(), is("New York"));
        assertThat(userInformation.getDefaultHighLight(), is(PasteHighLight.TEXT));
    }
}