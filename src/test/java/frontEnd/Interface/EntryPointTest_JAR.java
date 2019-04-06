package frontEnd.Interface;

import frontEnd.MessagingSystem.routing.Listing;
import frontEnd.MessagingSystem.routing.outputStructures.common.JacksonSerializer;
import frontEnd.MessagingSystem.routing.structure.Scarf.AnalyzerReport;
import frontEnd.argsIdentifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rule.engine.EngineType;
import soot.G;
import util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;
import static test.TestUtilities.*;

public class EntryPointTest_JAR {

    private final String tempFileOutTxt = Utils.osPathJoin(testPath, "testable-jar.txt");
    private final String tempFileOutXML_0 = Utils.osPathJoin(testPath, "testable-jar_012.xml");
    private final String tempFileOutXML_1 = Utils.osPathJoin(testPath, "testable-jar_123.xml");
    private final String tempFileOutXML_2 = Utils.osPathJoin(testPath, "testable-jar_234.xml");
    private final String tempStreamXML = Utils.osPathJoin(testPath, "testable-jar_Stream.xml");
    //region Attributes
    private EntryPoint engine;
    private ByteArrayOutputStream out;

    //region Scarf Properties
    private String assessment_start_ts;
    private String build_fw;
    private String build_fw_version;
    private String package_name;
    private String package_version;
    private String assess_fw;
    private String assess_fw_version;
    private String build_root_dir;
    private String package_root_dir;
    private String parser_fw;
    private String parser_fw_version;
    private String uuid;
    //endregion

    //endregion

    //region Test Environment Setup
    @Before
    public void setUp() throws Exception {
        //Cleaning the current scene since setup carries throughout the VM
        //tldr - one test setting up the scene will carry over to the next test, this'll stop that
        G.reset();

        engine = new EntryPoint();
        out = new ByteArrayOutputStream();

        //region Properties Setup
        assess_fw = "java-assess";
        assess_fw_version = "1.0.0c";
        assessment_start_ts = "1516116551.639144";
        build_fw = "c-assess";
        build_fw_version = "1.1.12";
        build_root_dir = "/home";
        package_name = "RigorityJ";
        package_root_dir = "CryptoGuard";
        package_version = "8675309";
        parser_fw = "example_tool";
        parser_fw_version = "x.y.z";
        uuid = "fa109792-9234-4jk2-9f68-alp9woofbeef";
        //endregion
    }

    @After
    public void tearDown() throws Exception {
        engine = null;
        out = null;

        //region Properties
        assess_fw = null;
        assess_fw_version = null;
        assessment_start_ts = null;
        build_fw = null;
        build_fw_version = null;
        build_root_dir = null;
        package_root_dir = null;
        package_name = null;
        package_root_dir = null;
        package_version = null;
        parser_fw = null;
        parser_fw_version = null;
        uuid = null;
        //endregion
    }
    //endregion

    //region Tests
    @Test
    public void testEnvironmentVariables() {
        String[] fileLists = new String[]{jarOne, pathToSchema};
        String[] dirLists = new String[]{srcOneGrv, srcOneGrvDep};

        for (String file : fileLists) {
            File tempFile = new File(file);

            assertTrue(tempFile.exists());
            assertTrue(tempFile.isFile());
        }

        for (String dir : dirLists) {
            File tempDir = new File(dir);

            assertTrue(tempDir.exists());
            assertTrue(tempDir.isDirectory());
        }


    }

    @Test
    public void main_TestableJar() {
        if (isLinux) {
            String args = "-in " + EngineType.JAR.getFlag() + " -s " + jarOne + " -d " + srcOneGrvDep + " -o " + tempFileOutTxt;

            try {
                engine.main(args.split(" "));

                List<String> results = Files.readAllLines(Paths.get(tempFileOutTxt), Charset.forName("UTF-8"));
                assertTrue(results.size() >= 10);


            } catch (Exception e) {
                e.printStackTrace();
                assertNull(e);
            }
        }
    }

    @Test
    public void main_TestableJar_Scarf() {
        if (isLinux) {
            String args = "-in " + EngineType.JAR.getFlag() + " -s " + jarOne + " -d " + srcOneGrvDep + " -m " + Listing.ScarfXML.getFlag() + " -o " + tempFileOutXML_0 + " -t" + " " + argsIdentifier.PRETTY.getArg();

            try {
                engine.main(args.split(" "));

                List<String> results = Files.readAllLines(Paths.get(tempFileOutXML_0), Charset.forName("UTF-8"));
                assertTrue(results.size() >= 1);


                AnalyzerReport report = AnalyzerReport.deserialize(JacksonSerializer.JacksonType.XML, new File(tempFileOutXML_0));

            } catch (Exception e) {
                e.printStackTrace();
                assertNull(e);
            }
        }
    }

    @Test
    public void main_TestableJar_Scarf_Args() {
        if (isLinux) {
            String args = "-in " + EngineType.JAR.getFlag() +
                    " -s " + jarOne +
                    " -d " + srcOneGrvDep +
                    " -m " + Listing.ScarfXML.getFlag() +
                    " -o " + tempFileOutXML_1 +
                    " -t" +
                    " -n" +
                    " -Sconfig " + scarfArgs;

            try {
                engine.main(args.split(" "));

                List<String> results = Files.readAllLines(Paths.get(tempFileOutXML_1), Charset.forName("UTF-8"));
                assertTrue(results.size() >= 1);


                AnalyzerReport report = AnalyzerReport.deserialize(JacksonSerializer.JacksonType.XML, new File(tempFileOutXML_1));
            } catch (Exception e) {
                e.printStackTrace();
                assertNull(e);
            }
        }
    }

    @Test
    public void main_TestableJar_Scarf_Stream() {
        if (isLinux) {
            String args = "-in " + EngineType.JAR.getFlag() + " -s " + jarOne + " -d " + srcOneGrvDep + " -m " + Listing.ScarfXML.getFlag() + " -o " + tempStreamXML + " -st" + " -t" + " " + argsIdentifier.PRETTY.getArg();

            try {
                engine.main(args.split(" "));

                List<String> results = Files.readAllLines(Paths.get(tempStreamXML), Charset.forName("UTF-8"));
                assertTrue(results.size() >= 1);


                AnalyzerReport report = AnalyzerReport.deserialize(JacksonSerializer.JacksonType.XML, new File(tempStreamXML));
            } catch (Exception e) {
                e.printStackTrace();
                assertNull(e);
            }
        }
    }

    @Test
    public void main_TestableJar_ScarfTimeStamp() {
        if (isLinux) {
            String args = "-in " + EngineType.JAR.getFlag() + " -s " + jarOne + " -d " + srcOneGrvDep + " -m " + Listing.ScarfXML.getFlag() + " -o " + tempFileOutXML_2 + " " + argsIdentifier.TIMESTAMP.getArg() + " " + argsIdentifier.PRETTY.getArg();

            try {
                engine.main(args.split(" "));

            } catch (Exception e) {
                e.printStackTrace();
                assertNull(e);
            }
        }
    }
    //endregion
}