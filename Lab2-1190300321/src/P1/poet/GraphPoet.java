/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 *
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 *
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 *
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 *
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 *
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   AF(graph)表示问题中建立的图
    // Representation invariant:
    //   所有加入的文字都应该是存在于语料库中的
    // Safety from rep exposure:
    //   graph定义为private final，必要的时候使用防御拷贝

    /**
     * Create a new poet with the graph from corpus (as described above).
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        BufferedReader readData = new BufferedReader(new FileReader(corpus));
        String rawData="";
        List<String>wordList=new ArrayList<>();
        Map<String, Integer>wordMap=new HashMap<>();
        //读取数据
        while((rawData=readData.readLine())!=null)
        {
            wordList.addAll(Arrays.asList(rawData.split(" ")));
        }
        readData.close();
        //把字符对加入图中
        for(int i=0;i<wordList.size()-1;i++)
        {
            Integer preWeight=0;
            String source=wordList.get(i).toLowerCase();
            String target=wordList.get(i+1).toLowerCase();
            String key=source+" "+target;//中间加上空格主要是为了防止出现类似于they+east==the+yeast这种问题
            if(wordMap.containsKey(key))
            {
                preWeight=wordMap.get(key);
            }
            preWeight++;
            graph.set(source,target,preWeight);
            wordMap.put(key,preWeight);
        }
        checkRep();
    }

    // TODO checkRep
    private void checkRep()
    {
        assert graph!=null;
    }
    /**
     * Generate a poem.
     *
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        StringBuilder strB=new StringBuilder();
        List<String>inputList=new ArrayList<>(Arrays.asList(input.split(" ")));
        for(int i=0;i<inputList.size()-1;i++)
        {
            int max=0;
            String addWord="";
            strB.append(inputList.get(i)).append(" ");
            String source=inputList.get(i).toLowerCase();
            String target=inputList.get(i+1).toLowerCase();
            Map<String,Integer>sourcesMap=graph.sources(target);
            Map<String,Integer>targetsMap=graph.targets(source);
            //选取有最大出现次数的词
            for(String s:sourcesMap.keySet())
            {
                if(targetsMap.containsKey(s)&&targetsMap.get(s)+targetsMap.get(s)>max)
                {
                    max=targetsMap.get(s)+targetsMap.get(s);
                    addWord=s;
                }
            }
            if(max!=0)
            {
                strB.append(addWord).append(" ");
            }
        }
        strB.append(inputList.get(inputList.size()-1));
        String str=strB.toString();
        checkRep();
        return str;
    }
    // TODO toString()
    @Override
    public String toString()
    {
        String s=graph.toString();
        checkRep();
        return s;
    }
}
