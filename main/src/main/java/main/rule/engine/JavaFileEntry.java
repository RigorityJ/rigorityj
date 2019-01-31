package main.rule.engine;

import main.frontEnd.MessagingSystem.AnalysisIssue;
import main.frontEnd.MessagingSystem.routing.EnvironmentInformation;
import main.util.FieldInitializationInstructionMap;
import main.util.NamedMethodMap;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author RigorityJTeam
 * Created on 2019-01-17.
 * @since 01.01.11
 *
 * <p>The method in the Engine handling Java File(s) Scanning.</p>
 */
public class JavaFileEntry implements EntryHandler {

    /**
     * {@inheritDoc}
     */
    public ArrayList<AnalysisIssue> NonStreamScan(EnvironmentInformation generalInfo) {

        ArrayList<AnalysisIssue> issues = generalInfo.getPrintOut() ? null : new ArrayList<AnalysisIssue>();


        //region Core Handling
        try {


            for (RuleChecker ruleChecker : CommonRules.ruleCheckerList) {
                ArrayList<AnalysisIssue> tempIssues = ruleChecker.checkRule(generalInfo.getSourceType(), generalInfo.getSource(), generalInfo.getDependencies(),
                        generalInfo.getPrintOut());

                if (!generalInfo.getPrintOut())
                    issues.addAll(tempIssues);
            }

            NamedMethodMap.clearCallerCalleeGraph();
            FieldInitializationInstructionMap.reset();

        } catch (IOException e) {

        }
        //endregion


        return issues;
    }


    /*public Stream<AnalysisIssue> StreamScan(EnvironmentInformation generalInfo) {
        return new ArrayList<AnalysisIssue>().stream();
    }*/
}
