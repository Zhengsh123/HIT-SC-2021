/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    // Testing strategy
    // 对文件输入进行划分：
    // 空文件，单行文件，多行文件
    // 对文件输入大小划分：
    // 小文本，较大文本
    // 对poem的生成过程划分：
    // 1.所有权重都是1 2.需要选择最大权重
    // 对toString的输入划分：
    // 空文件，非空文件
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    /*
     * 测试空文件输入
     */
    @Test
    public void testEmpty()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\empty.txt"));
        final String testWord="Test the code";
        assertEquals(testWord,nimoy.poem(testWord));
    }
    /*
     * 测试单行文件输入
     */
    @Test
    public void testoneLine()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\oneLine.txt"));
        final String testWord="Test the code";
        assertEquals("Test of the code",nimoy.poem(testWord));

    }
    /*
     * 测试多行文件输入
     */
    @Test
    public void testmutiLines()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\mutiLines.txt"));
        final String testWord="Seek to explore new and exciting synergies!";
        assertEquals("Seek to explore strange new life and exciting synergies!",nimoy.poem(testWord));

    }
    /*
     * 测试不需要选择权重的情况
     */
    @Test
    public void testnoSelect()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\noSelect.txt"));
        final String testWord="Test the code";
        assertEquals("Test of the code",nimoy.poem(testWord));
    }
    /*
     * 测试需要选择权重的情况
     */
    @Test
    public void testSelect()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\Select.txt"));
        final String testWord="Test the project";
        assertEquals("Test of the simple project",nimoy.poem(testWord));
    }
    /*
     *
     * 测试较大文本
     */
    @Test
    public void testPiece()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\piece.txt"));
        final String testWord="life like flowers and I like life .";
        assertEquals("life like summer flowers and I like life .",nimoy.poem(testWord));
    }
    /*
     * 测试toString,分为空文件与非空文件
     */
    @Test
    public void testtoString()throws IOException
    {
        final GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/empty.txt"));
        assertEquals("",nimoy.toString());
        final GraphPoet nimoy1 = new GraphPoet(new File("test/P1/poet/oneline.txt"));
        assertEquals("this->is weight:1\n" + "is->a weight:1\n" + "a->test weight:1\n" + "test->of weight:1\n" + "of->the weight:1\n" + "the->simple weight:1\n" +
                "simple->project. weight:1\n",nimoy1.toString());
    }
}
