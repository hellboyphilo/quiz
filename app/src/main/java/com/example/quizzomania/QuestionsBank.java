package com.example.quizzomania;

import java.util.ArrayList;
import java.util.List;

public class QuestionsBank {
    private static List<Modelclass> codingQuestions(){
        final List<Modelclass> modelclass = new ArrayList<>();

        final Modelclass modelclass1 = new Modelclass("what is size of int variable?","16 bit","8 bit","32 bit","64 bit","32 bit","");
        final Modelclass modelclass2 = new Modelclass("Which component is used to compile, debug and execute the java programs?","JRE","JIT","JDK","JVM","JDK","");

        modelclass.add(modelclass1);
        modelclass.add(modelclass2);

        return modelclass;
    }

    private static List<Modelclass> gkQuestions(){
        final List<Modelclass> modelclass = new ArrayList<>();

        final Modelclass modelclass1 = new Modelclass("what is size of int variable?","16 bit","8 bit","32 bit","64 bit","32 bit","");
        final Modelclass modelclass2 = new Modelclass("Which component is used to compile, debug and execute the java programs?","JRE","JIT","JDK","JVM","JDK","");

        modelclass.add(modelclass1);
        modelclass.add(modelclass2);

        return modelclass;
    }

    private static List<Modelclass> sportsQuestions(){
        final List<Modelclass> modelclass = new ArrayList<>();

        final Modelclass modelclass1 = new Modelclass("what is size of int variable?","16 bit","8 bit","32 bit","64 bit","32 bit","");
        final Modelclass modelclass2 = new Modelclass("Which component is used to compile, debug and execute the java programs?","JRE","JIT","JDK","JVM","JDK","");

        modelclass.add(modelclass1);
        modelclass.add(modelclass2);

        return modelclass;
    }

    private static List<Modelclass> moviesQuestions(){
        final List<Modelclass> modelclass = new ArrayList<>();

        final Modelclass modelclass1 = new Modelclass("what is size of int variable?","16 bit","8 bit","32 bit","64 bit","32 bit","");
        final Modelclass modelclass2 = new Modelclass("Which component is used to compile, debug and execute the java programs?","JRE","JIT","JDK","JVM","JDK","");

        modelclass.add(modelclass1);
        modelclass.add(modelclass2);

        return modelclass;
    }
    public static List<Modelclass> getQuestions(String selectedTopicName){
        switch (selectedTopicName){
            case "coding":
                return codingQuestions();
            case "gk":
                return gkQuestions();
            case "sports":
                return sportsQuestions();
            default:
                return moviesQuestions();
        }
    }
}
