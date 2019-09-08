package frontEnd.MessagingSystem.routing.outputStructures.common;

import CWE_Reader.CWE;
import CWE_Reader.CWEList;
import frontEnd.MessagingSystem.AnalysisIssue;
import frontEnd.MessagingSystem.AnalysisLocation;
import frontEnd.MessagingSystem.routing.EnvironmentInformation;
import frontEnd.MessagingSystem.routing.structure.Scarf.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>ScarfXML class.</p>
 *
 * @author CryptoguardTeam
 * Created on 3/2/19.
 * @version 03.07.01
 * @since 03.03.00
 *
 * <p>The common utilities class for ScarfXML marshalling.</p>
 */
@Log4j2
public class ScarfXML {

    /**
     * <p>marshalling.</p>
     *
     * @param info a {@link frontEnd.MessagingSystem.routing.EnvironmentInformation} object.
     * @return a {@link frontEnd.MessagingSystem.routing.structure.Scarf.AnalyzerReport} object.
     */
    public static AnalyzerReport marshalling(EnvironmentInformation info) {
        AnalyzerReport report = new AnalyzerReport();

        //region Setting Attributes
        report.setAssessFw(info.getAssessmentFramework());
        report.setAssessFwVersion(info.getAssessmentFrameworkVersion());
        report.setAssessmentStartTs(info.getAssessmentStartTime());
        report.setBuildFw(info.getBuildFramework());
        report.setBuildFwVersion(info.getBuildFrameworkVersion());
        report.setPackageName(info.getPackageName());
        report.setPackageVersion(info.getPackageVersion());
        report.setPackageRootDir(info.getPackageRootDir());
        report.setParserFw(info.getParserName());
        report.setParserFwVersion(info.getParserVersion());
        report.setPlatformName(info.getPlatformName());
        report.setToolName(info.getToolFramework());
        report.setToolVersion(info.getToolFrameworkVersion());
        report.setUuid(info.getUUID());
        report.setBuildRootDir(info.getBuildRootDir());
        //endregion

        return report;
    }

    /**
     * <p>marshalling.</p>
     *
     * @param issue    a {@link frontEnd.MessagingSystem.AnalysisIssue} object.
     * @param cwes     a {@link CWE_Reader.CWEList} object.
     * @param fileName a {@link java.lang.String} object.
     * @param id       a {@link java.lang.Integer} object.
     * @param buildId  a {@link java.lang.Integer} object.
     * @param xPath    a {@link java.lang.String} object.
     * @return a {@link frontEnd.MessagingSystem.routing.structure.Scarf.BugInstance} object.
     */
    public static BugInstance marshalling(AnalysisIssue issue, CWEList cwes, String fileName, Integer id, Integer buildId, String xPath) {
        BugInstance instance = new BugInstance();

        //region Setting the instance

        instance.setId(id);
        instance.setBugCode(issue.getRuleId().toString());
        instance.setBugGroup(issue.getRule().getDesc());
        instance.setBugMessage(issue.getRule().getDesc());

        if (buildId != null || xPath != null) {
            BugTrace trace = new BugTrace();

            if (buildId != null)
                trace.setBuildId(buildId);

            if (xPath != null)
                trace.setAssessmentReportFile(xPath);

            instance.setBugTrace(trace);
        }

        for (CWE cwe : issue.getRule().retrieveCWEInfo(cwes))
            instance.getCweId().add(String.valueOf(cwe.getId()));

        if (StringUtils.isNotBlank(issue.getClassName())) {
            instance.setClassName(issue.getClassName());
        }

        //region Setting Methods If there are any, currently the first is the primary one
        if (!issue.getMethods().isEmpty()) {
            for (int methodKtr = 0; methodKtr < issue.getMethods().size(); methodKtr++) {
                Method newMethod = new Method();

                newMethod.setId(methodKtr);
                newMethod.setPrimary(methodKtr == 0);
                newMethod.setSelf((String) issue.getMethods().get(methodKtr));

                instance.addMethod(newMethod);
            }
        }
        //endregion

        //region Setting Bug Locations
        if (!issue.getLocations().isEmpty()) {
            for (int locationKtr = 0; locationKtr < issue.getLocations().size(); locationKtr++) {
                Location newLocation = new Location();
                AnalysisLocation createdLoc = issue.getLocations().get(locationKtr);

                //newLocation.setId(locationKtr); //TODO - Look at this
                //newLocation.setPrimary(locationKtr == 0); //TODO - Look at this

                if (createdLoc.getLineStart() != -1)
                    newLocation.setStartLine(createdLoc.getLineStart());

                newLocation.setSourceFile(issue.getFullPathName());

                if (createdLoc.getLineEnd() > 0 && !createdLoc.getLineEnd().equals(createdLoc.getLineStart())) {
                    newLocation.setEndLine(createdLoc.getLineEnd());
                }
                instance.addBugLocation(newLocation);
            }
        } else {
            Location newLocation = new Location();
            newLocation.setSourceFile(issue.getFullPathName());
            // newLocation.setPrimary(true); //TODO - Look at this
            instance.addBugLocation(newLocation);
        }
        //endregion

        //region Setting Bug Message
        StringBuilder outputMessage = new StringBuilder();
        String info = StringUtils.trimToNull(issue.getInfo());

        if (info != null)
            outputMessage.append(info).append(". ");

        outputMessage.append(issue.getRule().getDesc()).append(".");

        instance.setBugMessage(outputMessage.toString());
        //endregion

        //region Setting BugTrace
        BugTrace trace = new BugTrace();

        if (buildId != null)
            trace.setBuildId(buildId);

        trace.setAssessmentReportFile(fileName);

        instance.setBugTrace(trace);
        //endregion

        //endregion

        return instance;
    }

    /**
     * The method for this output to create error messages
     *
     * @param message -  the string of the message to be sent to the output
     * @return string - the xml format of the error message
     */
    private String creatingErrorMessage(String message) {
        StringBuilder output = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        output.append("<ERROR>");
        output.append(message);
        output.append("</ERROR>");
        return output.toString();
    }

    /**
     * The method to write the Footer for the ScarfXML output.
     *
     * @param info a {@link frontEnd.MessagingSystem.routing.EnvironmentInformation} object
     * @return string - the xml format of the error message
     */
    public static String writeFooter(EnvironmentInformation info) {
        String footer = "";
        String prettyTab = info.prettyPrint() ? "\t" : "";
        String prettyLine = info.prettyPrint() ? "\n" : " ";

        StringBuilder commentedFooter = new StringBuilder();

        if (info.getSootErrors() != null && info.getSootErrors().split("\n").length >= 1) {
            log.info("Adding the Soot Errors");
            commentedFooter.append(prettyTab).append(info.getSootErrors().replaceAll("\n", prettyLine)).append(prettyLine);
        }

        if (info.isShowTimes()) {
            log.trace("Adding the time measurements");
            commentedFooter.append("Analysis Timing (ms): ").append(info.getAnalyisisTime()).append(".").append(prettyLine);
        }

        if (StringUtils.isNotBlank(commentedFooter.toString()))
            footer = prettyLine + "<!--" + prettyLine + commentedFooter.toString() + "-->";

        return footer;
    }

    /**
     * <p>marshalling.</p>
     *
     * @param info                a {@link frontEnd.MessagingSystem.routing.EnvironmentInformation} object.
     * @param roundedSliceAverage a double.
     * @return a {@link frontEnd.MessagingSystem.routing.structure.Scarf.Heuristics} object.
     */
    public static Heuristics marshalling(EnvironmentInformation info, double roundedSliceAverage) {
        return new Heuristics(
                info.getNUM_ORTHOGONAL(),
                info.getNUM_CONSTS_TO_CHECK(),
                info.getNUM_SLICES(),
                info.getNUM_HEURISTIC(),
                roundedSliceAverage,
                info.getDEPTH_COUNT()
        );
    }
}
