/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Umang
 */
public class BtreeTester {
    
    public static IntBalancedSet ReadTreeFromTextFile(String FileName) throws IOException, CloneNotSupportedException 
    {
        int element;
        
        Scanner fileIn = new Scanner(new File(FileName));
        
        IntBalancedSet treeA = new IntBalancedSet();
        //treeA.MAXIMUM = fileIn.nextInt();
        //treeA.MINIMUM = fileIn.nextInt();
        while(fileIn.hasNext())
        {
            element = fileIn.nextInt();
            if(!treeA.contains(element))
            {
                treeA.add(element);
                System.out.print(element + " ");
            }
            else
                System.out.println(element + " already exists...");
        }
        System.out.println();
        System.out.println("End of Reading!");
        return treeA;
    }
    
    public static void main(String[] args) throws IOException, CloneNotSupportedException 
    {
        IntBalancedSet treeA = new IntBalancedSet();
        IntBalancedSet treeB = new IntBalancedSet();
        IntBalancedSet treeC = new IntBalancedSet();
        
        PrintWriter outTreeC = new PrintWriter("treeC.txt");
        
        System.out.println("1) Input (read) treeA from a text file: \n" +"\n" + "Reading a TREE from treeA.txt file:"
                + "\n Note: This tree doesn't contain duplicate values.");
        treeA = ReadTreeFromTextFile("treeA.txt");
        System.out.println(" Display treeA: (with indentation) ");
        treeA.print(8);
         
        System.out.println("2) Input (read) treeB from a text file: \n" + "\n" + "Reading a TREE from treeB.txt file:");
        treeB = ReadTreeFromTextFile("treeB.txt");
        System.out.println(" Display treeB: (with indentation) ");
        treeB.print(8);
        treeA.addTree(treeB);
        treeC = treeA;
        
        System.out.println("3) The treeB has been added to treeA => tree C \n" + "Writing this tree to treeC.txt\n" +
            "\n" + "\n" + "   writing treeC to a text file ...\n" + "\n" + "   Display treeC:");
        treeC.print(8);
        treeC.writeT(outTreeC);
        outTreeC.close();
        
        
        System.out.println("4) Enter the target element to SEARCH in treeC :");
        Scanner kb = new Scanner(System.in);
        int target = kb.nextInt();
        if(treeC.contains(target))
            System.out.println("Target found!");
        else
            System.out.println("Target not found!");
         
         // size() method has one argument which will initialize counter.
        System.out.println("5) treeD's SIZE = " + treeC.size(0));
         
        System.out.println("6) Enter a Value X for the NEW node to be ADDED: ");
        int element = kb.nextInt();
        treeC.add(element);
        System.out.println("Display the NEW TreeC AFTER adding a tree with X ");
        treeC.print(8);
        
        System.out.println("7) Enter the Value Y of the node to be REMOVED:");
        int delete = kb.nextInt();
        boolean ans = treeC.remove(delete);
        System.out.println("Result of removing: " + ans);
        
        if(ans)
        {
            System.out.println("Display the NEW TreeD after removing the node with Y: ");
            treeC.print(8);
        }
        else
        {
            System.out.println("Display the New TreeD: ");
            treeC.print(8);
        }
        
        System.out.println("!!! THE END !!!");
    }
    
}
