package it.unibz.inf.ontop.temporal.mapping;

import it.unibz.inf.ontop.model.OBDADataFactory;
import it.unibz.inf.ontop.model.Variable;
import it.unibz.inf.ontop.temporal.mapping.impl.TemporalMappingIntervalImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.unibz.inf.ontop.model.impl.OntopModelSingletons.DATA_FACTORY;

public class IntervalQueryParser {

    public static TemporalMappingInterval parse(String input){
        OBDADataFactory odf = DATA_FACTORY;
        boolean beginInclusive = false;
        boolean endInclusive = false;
        Variable begin;
        Variable end;
        String strBegin = "";
        String strEnd = "";
        String strBeginInclusive;
        String strEndInclusive;

        Pattern variablePattern = Pattern.compile("(?i)\\{[\\w.-]+\\}");
        Matcher m = variablePattern.matcher(input);
        if(m.find()) {
            strBegin = m.group();
            strBegin = strBegin.substring(1, strBegin.length()-1);
        }
        if(m.find()) {
            strEnd = m.group();
            strEnd = strEnd.substring(1, strEnd.length()-1);
        }
        begin = odf.getVariable(strBegin);
        end = odf.getVariable(strEnd);

        strBeginInclusive = input.substring(0,1);
        strEndInclusive = input.substring(input.length()-1, input.length());

        if(strBeginInclusive.equals("["))
            beginInclusive = true;
        else if(strBeginInclusive.equals("("))
            beginInclusive = false;

        if(strEndInclusive.equals("]"))
            endInclusive = true;
        else if(strEndInclusive.equals(")"))
            endInclusive = false;

        return new TemporalMappingIntervalImpl(beginInclusive,endInclusive,begin,end);
    }

    public static boolean temporalMappingIntervalValidator(String input){
        Pattern intervalRegexPattern = Pattern.compile("(?i)[\\[\\(]\\s*\\{[\\w.-]+\\}(\\^{2}[\\w.-]+:(date|datetime|time))?\\s*,\\s*\\{[\\w.-]+\\}(\\^{2}[\\w.-]+:(date|datetime|time))?\\s*[\\)\\]]");
        Matcher m = intervalRegexPattern.matcher(input);
        return m.matches();
    }
}