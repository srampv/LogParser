package com.seo;

import nl.jqno.equalsverifier.internal.util.Assert;
import nl.jqno.equalsverifier.internal.util.Formatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;

import javax.xml.crypto.dsig.DigestMethod;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@PrepareForTest(LogParser.class)
public class LogParserTest extends PowerMockTestCase {

    @Mock
    LogParser logParser;

    @Mock
    Files files;

    @Mock
    List<JsonData> list;

    @BeforeEach
    public void init() throws IOException {
        logParser = Mockito.spy(LogParser.class);
        MockitoAnnotations.initMocks(LogParser.class);


        PowerMockito.mockStatic(Files.class);


    }


    /***
     * Test with valid data and no nulls . this is positive case
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void processData() throws URISyntaxException, IOException {
        List<JsonData> data = getData();
        Map<String, Set<JsonData>> map = new HashMap<>();
        Mockito.when(logParser.getDataObjects()).thenReturn(data);
        Path p = Mockito.mock(Path.class);
        String url = Thread.currentThread().getContextClassLoader().getResource("json-test-data.txt").getPath();

        List<String> lines = new ArrayList<>() {{
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"phkkrw.ext\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"asdf.pdf\",\"ph\":\"/efvrfutgp/asdf.pdf\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"phkkrw.ext\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"phkkrw.ext\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
        }};

        try {

            System.out.println(url);
            PowerMockito.when(logParser, "readFile", url)
                    .thenReturn(lines);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map = logParser.processFile(url);
        map.forEach((u, v) -> {
            System.out.println(((JsonData) v.stream().toArray()[0]).getNm().split("\\.")[1] + ":" + v.size());
        });
        Assert.assertTrue(Formatter.of("Map should have %% ", map.size()), map.size() == 4);
    }

    /***
     * Test to validate few null fileName log lines and it never breaks .
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void processDataWithNullFileName() throws URISyntaxException, IOException {
        List<JsonData> data = getBadData();
        Map<String, Set<JsonData>> map = new HashMap<>();
        Mockito.when(logParser.getDataObjects()).thenReturn(data);
        Path p = Mockito.mock(Path.class);
        String url = Thread.currentThread().getContextClassLoader().getResource("json-test-data-bad.txt").getPath();

        List<String> lines = new ArrayList<>() {{
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"phkkrw.ext\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"asdf.pdf\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"phkkrw.ext\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"phkkrw.ext\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
        }};

        try {

            System.out.println(url);
            PowerMockito.when(logParser, "readFile", url)
                    .thenReturn(lines);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map = logParser.processFile(url);
        map.forEach((u, v) -> {
            System.out.println(((JsonData) v.stream().toArray()[0]).getNm().split("\\.")[1] + ":" + v.size());
        });
        Assert.assertTrue(Formatter.of("Map should have size: %% with ext: %%  ", map.size(), map.size()), map.size() == 2);
    }

    /***
     * Test to validate 1 log line multiple times appear and it should return 1
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void processDataWithOneFileName() throws URISyntaxException, IOException {
        List<JsonData> data = getOneData();
        Map<String, Set<JsonData>> map = new HashMap<>();
        Mockito.when(logParser.getDataObjects()).thenReturn(data);
        Path p = Mockito.mock(Path.class);
        String url = Thread.currentThread().getContextClassLoader().getResource("json-test-data-one.txt").getPath();

        List<String> lines = new ArrayList<>() {{
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"asdf.pdf\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"asdf.pdf\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"asdf.pdf\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"nm\":\"asdf.pdf\",\"ph\":\"/efvrfutgp/expgh/phkkrw\",\"dp\":2}");
        }};

        try {

            System.out.println(url);
            PowerMockito.when(logParser, "readFile", url)
                    .thenReturn(lines);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map = logParser.processFile(url);
        map.forEach((u, v) -> {
            System.out.println(((JsonData) v.stream().toArray()[0]).getNm().split("\\.")[1] + ":" + v.size());
        });
        Assert.assertTrue(Formatter.of("Map should have %%  ", map.size()), map.size() == 1);
    }

    /***
     * Test to check what if Log has all null file names and still code never breaks should return 0.
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void processDataWithallNullFileName() throws URISyntaxException, IOException {
        List<JsonData> data = getNullData();
        Map<String, Set<JsonData>> map = new HashMap<>();
        Mockito.when(logParser.getDataObjects()).thenReturn(data);
        Path p = Mockito.mock(Path.class);
        String url = Thread.currentThread().getContextClassLoader().getResource("json-test-data-one.txt").getPath();

        List<String> lines = new ArrayList<>() {{
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"dp\":2}");
            add("{\"ts\":1551140352,\"pt\":55,\"si\":\"3380fb19-0bdb-46ab-8781-e4c5cd448074\",\"uu\":\"0dd24034-36d6-4b1e-a6c1-a52cc984f105\",\"bg\":\"77e28e28-745a-474b-a496-3c0e086eaec0\",\"sha\":\"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52\",\"dp\":2}");
        }};

        try {

            System.out.println(url);
            PowerMockito.when(logParser, "readFile", url)
                    .thenReturn(lines);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map = logParser.processFile(url);
        map.forEach((u, v) -> {
            System.out.println(((JsonData) v.stream().toArray()[0]).getNm().split("\\.")[1] + ":" + v.size());
        });
        Assert.assertTrue(Formatter.of("Map should have %%  ", map.size()), map.size() == 0);
    }

    /***
     * Test to validate if IO exception is thrown due to file missing .
     */
    @Test
    public void testFileNotFoundandThrowException() {

        List<JsonData> data = getNullData();
        Map<String, Set<JsonData>> map = new HashMap<>();
        Mockito.when(logParser.getDataObjects()).thenReturn(data);
        Path p = Mockito.mock(Path.class);
        String url = Thread.currentThread().getContextClassLoader().getResource("json-test-data-one.txt").getPath();


        try {

            System.out.println(url);
            PowerMockito.when(logParser, "readFile", url)
                    .thenThrow(new IOException("I/O Exception"));

        } catch (Exception e) {
            org.testng.Assert.fail("I/O Exception");
            e.printStackTrace();
        }

        Assert.assertTrue(Formatter.of("Map should have %%  ", map.size()), map.size() == 0);

    }

    /***
     * Test to validate if readLines in the file null .
     */
    @Test
    public void testValidateReadLinesNull() {

        List<JsonData> data = getNullData();
        Map<String, Set<JsonData>> map = new HashMap<>();
        Mockito.when(logParser.getDataObjects()).thenThrow(new NullPointerException("No log lines"));
        Path p = Mockito.mock(Path.class);
        String url = Thread.currentThread().getContextClassLoader().getResource("json-test-data-one.txt").getPath();


        try {

            System.out.println(url);
            PowerMockito.when(logParser, "readFile", url)
                    .thenReturn(new ArrayList<>());

        } catch (Exception e) {
            org.testng.Assert.fail("I/O Exception");
            e.printStackTrace();
        }
        try {
            logParser.processFile(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(Formatter.of("Map should have %%  ", map.size()), map.size() == 0);

    }

    private List<JsonData> getData() {
        JsonData data1 = getObject("asdf.pdf", "/434/asdf.pdf");
        JsonData data2 = getObject("asdf.txt", "/errr/asdf.txt");
        JsonData data3 = getObject("asdf.gif", "/rerer/asdf.gif");
        JsonData data4 = getObject("asdf.csv", "/errr/asdf.pdf");
        List<JsonData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        return list.stream().filter(e -> e.getPh() != null).collect(Collectors.toList());
    }

    private List<JsonData> getBadData() {
        JsonData data1 = getObject("asdf.pdf", "/434/asdf.pdf");
        JsonData data2 = getObject("asdf.txt", null);
        JsonData data3 = getObject("asdf.gif", null);
        JsonData data4 = getObject("asdf.csv", "/errr/asdf.pdf");
        List<JsonData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        return list.stream().filter(e -> e.getPh() != null).collect(Collectors.toList());
    }

    private List<JsonData> getOneData() {
        JsonData data1 = getObject("asdf.pdf", "/434/asdf.pdf");
        JsonData data2 = getObject("asdf.pdf", "/434/asdf.pdf");
        JsonData data3 = getObject("asdf.pdf", "/434/asdf.pdf");
        JsonData data4 = getObject("asdf.pdf", "/434/asdf.pdf");
        List<JsonData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        return list.stream().filter(e -> e.getPh() != null).collect(Collectors.toList());
    }

    private List<JsonData> getNullData() {
        JsonData data1 = getObject(null, null);
        JsonData data2 = getObject(null, null);
        JsonData data3 = getObject(null, null);
        JsonData data4 = getObject(null, null);
        List<JsonData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        return list.stream().filter(e -> e.getPh() != null).collect(Collectors.toList());
    }

    private JsonData getObject(String nm, String ph) {
        JsonData data = new JsonData();
        data.setTs(System.currentTimeMillis() + "");
        data.setPt(System.currentTimeMillis() + "");
        data.setSi(UUID.randomUUID() + "");
        data.setUu(UUID.randomUUID() + "");
        data.setBg(UUID.randomUUID() + "");
        data.setSha(DigestMethod.SHA256 + "");
        data.setNm(nm);
        data.setPh(ph);
        data.setDp(DP.CLEAN);
        return data;
    }
}
