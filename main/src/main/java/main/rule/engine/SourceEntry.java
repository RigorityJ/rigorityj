package main.rule.engine;

import main.frontEnd.MessagingSystem.AnalysisIssue;
import main.frontEnd.MessagingSystem.routing.EnvironmentInformation;
import main.util.BuildFileParser;
import main.util.BuildFileParserFactory;
import main.util.FieldInitializationInstructionMap;
import main.util.NamedMethodMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>SourceEntry class.</p>
 *
 * @author RigorityJTeam
 * Created on 2018-12-14.
 * @version $Id: $Id
 * @since 01.01.06
 *
 * <p>The method in the Engine handling Source Scanning</p>
 */
public class SourceEntry implements EntryHandler {

    /**
     * {@inheritDoc}
     */
    public ArrayList<AnalysisIssue> NonStreamScan(EnvironmentInformation generalInfo) {
        ArrayList<AnalysisIssue> issues = generalInfo.getPrintOut() ? null : new ArrayList<AnalysisIssue>();

        try {
            BuildFileParser buildFileParser = BuildFileParserFactory.getBuildfileParser(generalInfo.getSource().get(0));

            Map<String, List<String>> moduleVsDependency = buildFileParser.getDependencyList();
            List<String> analyzedModules = new ArrayList<>();

            for (String module : moduleVsDependency.keySet()) {

                if (!analyzedModules.contains(module)) {

                    List<String> dependencies = moduleVsDependency.get(module);
                    List<String> otherdependencies = new ArrayList<>();

                    for (String dependency : dependencies) {

                        String dependencyModule;

                        if (dependency.equals(generalInfo.getSource().get(0) + "/src/main/java"))
                            dependencyModule = generalInfo.getSource().get(0).substring(generalInfo.getSource().get(0).lastIndexOf("/") + 1);
                        else
                            dependencyModule = dependency.substring(generalInfo.getSource().get(0).length() + 1, dependency.length() - 14);

                        otherdependencies.add(dependency.substring(0, dependency.length() - 13) + generalInfo.getDependencies());

                        analyzedModules.add(dependencyModule);
                    }

                    generalInfo.startAnalysis();
                    for (RuleChecker ruleChecker : CommonRules.ruleCheckerList) {
                        ArrayList<AnalysisIssue> tempIssues = ruleChecker.checkRule(EngineType.DIR, dependencies, otherdependencies, generalInfo.getPrintOut(), generalInfo.getSourcePaths());

                        if (!generalInfo.getPrintOut())
                            issues.addAll(tempIssues);
                    }
                    generalInfo.stopAnalysis();

                    NamedMethodMap.clearCallerCalleeGraph();
                    FieldInitializationInstructionMap.reset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issues;
    }
}