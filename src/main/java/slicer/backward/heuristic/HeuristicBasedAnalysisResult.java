package slicer.backward.heuristic;

import analyzer.backward.UnitContainer;
import slicer.backward.property.PropertyAnalysisResult;
import soot.SootMethod;

import java.util.List;
import java.util.Map;

/**
 * <p>HeuristicBasedAnalysisResult class.</p>
 *
 * @author franceme
 * @version 03.07.01
 */
public class HeuristicBasedAnalysisResult {

    private String instruction;
    private SootMethod method;
    private List<UnitContainer> analysis;
    private Map<String, List<PropertyAnalysisResult>> propertyUseMap;

    /**
     * <p>Constructor for HeuristicBasedAnalysisResult.</p>
     *
     * @param instruction    a {@link java.lang.String} object.
     * @param method         a {@link soot.SootMethod} object.
     * @param analysis       a {@link java.util.List} object.
     * @param propertyUseMap a {@link java.util.Map} object.
     */
    public HeuristicBasedAnalysisResult(String instruction,
                                        SootMethod method,
                                        List<UnitContainer> analysis,
                                        Map<String, List<PropertyAnalysisResult>> propertyUseMap) {
        this.instruction = instruction;
        this.method = method;
        this.analysis = analysis;
        this.propertyUseMap = propertyUseMap;
    }

    /**
     * <p>Getter for the field <code>instruction</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * <p>Getter for the field <code>method</code>.</p>
     *
     * @return a {@link soot.SootMethod} object.
     */
    public SootMethod getMethod() {
        return method;
    }

    /**
     * <p>Getter for the field <code>analysis</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<UnitContainer> getAnalysis() {
        return analysis;
    }

    /**
     * <p>Getter for the field <code>propertyUseMap</code>.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    public Map<String, List<PropertyAnalysisResult>> getPropertyUseMap() {
        return propertyUseMap;
    }
}
