/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

import java.io.PrintWriter;

/**
 *
 * @author Umang
 */
public class IntBalancedSet implements Cloneable {

    public static int MINIMUM = 1;
    public static int MAXIMUM = 2*MINIMUM;
    int dataCount;
    int[] data = new int[MAXIMUM + 1];
    int childCount;
    IntBalancedSet[] subset = new IntBalancedSet[MAXIMUM + 2]; 

    public IntBalancedSet()
    {
        dataCount = 0;
        childCount = 0;
    }
    
    public int getLeftMostData()
    {
        if(subset[0] == null)
            return data[0];
        else
            return subset[0].getLeftMostData();
    }
    
    public boolean isLeaf()
    {
        if(subset[0]==null)
            return true;
        else
            return false;
    }
    
    public boolean contains(int target)
    {
        int i=0;
        for(int j = 0; j<data.length; j++)
        {
            if(data[j]>=target)
            {
                i=j;
                break;
            }
            else
                i=dataCount;
        }
        if(target==data[i])
            return true;
        else if(childCount==0)
            return false;
        else
            return subset[i].contains(target);
    }

    public void writeT(PrintWriter treeC)
    {
        treeC.print(MAXIMUM + " " + MINIMUM + " ");
        writeTree(treeC);
    }
    
    public void writeTree(PrintWriter treeC)
    {
        int i;
        
        for(i=0; i<dataCount; i++)
        {
            treeC.print(data[i] + " ");
        }
        
        for(i=0; i<childCount; i++)
        {
            subset[i].writeTree(treeC);
        }
    }
    
    public void add(int element) throws CloneNotSupportedException 
    {
        if(!contains(element))
        {
            looseAdd(element);
            //After loose addition root might have more than maximum(i.e. 2) elements.
            if(dataCount>2)
            {
                IntBalancedSet newRoot = new IntBalancedSet();
                for(int x = 0; x< dataCount; x++)
                {
                    newRoot.data[x] = data[x];
                }
                for(int y=0; y<childCount;y++)
                {
                    newRoot.subset[y] = subset[y];
                }
                newRoot.dataCount = dataCount;
                newRoot.childCount = childCount;
                for(int x = 0; x< dataCount; x++)
                {
                    data[x] = 0;
                }
                dataCount=0;
                childCount=1;
                subset[0] = newRoot;
                subset[1] = subset[2] = subset[3] = null;
                fixExcess(0);
            }
        }
        else
            System.out.println(element + " already exists...");
    }

    private void looseAdd(int element) throws CloneNotSupportedException 
    {
        int i = 0;
        for(int j = 0; j<data.length; j++)
        {
            if(data[j]>=element)
            {
                i=j;
                break;
            }
            else
                i=dataCount;
        }
        if(data[i]==element)
        {
            System.out.println("Element already exists...");
        
        }
        else if(childCount==0)
        {
            for(int x = dataCount ; x >= 0 ; x--)
            {
                if(i<x)
                {
                    data[x] = data[x-1];
                }
                else
                {
                    data[i] = element;
                    dataCount++;
                    break;
                }
            }
        }   
        
        else
        {
            subset[i].looseAdd(element);
            if(subset[i].dataCount > MAXIMUM)
                fixExcess(i);
        }
    }
    
    private void fixExcess(int i) throws CloneNotSupportedException 
    {
        if(data[i]!=0)
        {
            for(int x = dataCount ; x >= 0 ; x--)
            {
                if(i<x)
                {
                    data[x] = data[x-1];
                }
                else
                {
                    data[i] = subset[i].data[(subset[i].dataCount/2)];
                    dataCount++;
                    break;
                }
            }
        }
        else
        {
            data[i] = subset[i].data[(subset[i].dataCount/2)];
            dataCount++;
        }
        
        IntBalancedSet leftChild = new IntBalancedSet();
        IntBalancedSet rightChild = new IntBalancedSet();
        leftChild.dataCount = 1;
        rightChild.dataCount = 1;
        
        for(int p=0;p<MINIMUM;p++)
        {
            leftChild.data[p] = subset[i].data[p];
            rightChild.data[p] = subset[i].data[p+1+1];
        }
        
        int subCh = (subset[i].childCount/2);
        for(int p=0; p<subCh; p++)
        {
            leftChild.subset[p] = subset[i].subset[p];
            rightChild.subset[p] = subset[i].subset[p+subCh];
        }
        
        if(subCh>0)
        {
            leftChild.childCount = 2;
            rightChild.childCount = 2;
        }
        
        subset[childCount] = new IntBalancedSet();
        for(int p=childCount; p>i; p--)
        {
            subset[p] = subset[p-1];
        }
        
        childCount++;
        subset[i]=leftChild;
        subset[i+1]=rightChild;
        
    }
    
    public void addTree(IntBalancedSet treeB) throws CloneNotSupportedException 
    {
        if(treeB!=null)
        {
            for(int i=0; i<treeB.dataCount; i++)
            {
                add(treeB.data[i]);
            }
        
            for(int i=0; i<childCount; i++)
            {
                addTree(treeB.subset[i]);
            }
        }
        
    }
    
    public int size(int count) throws CloneNotSupportedException 
    {
        
        if(this == null)
            return 0;
        else
        {
            for(int i=0; i<this.dataCount; i++)
            {
                count++;
            }
        
            for(int i=0; i<childCount; i++)
            {
                 count = (this.subset[i]).size(count);
            }
        }
        return count;
    }
    
    public boolean remove(int target) 
    {
        boolean answer = looseRemove(target);
        if(dataCount==0 && childCount==1)
        {
            dataCount = subset[0].dataCount;
            System.arraycopy(subset[0].data, 0, data, 0, subset[0].data.length);
            childCount = subset[0].childCount;
            subset = subset[0].subset;
        }
        return answer;
    }
    
    private boolean looseRemove(int target)
    {        
        int i = 0;
        for(int j = 0; j<data.length; j++)
        {
            if(target<=data[j])
            {
                i=j;
                break;
            }
            else
                i=dataCount;
        }
        if(subset[i]==null && data[i]!=target)
            return false;
        else if(subset[i]==null && data[i]==target)
        {
            int[] newData = new int[MAXIMUM + 1];
            if(i==1)
                System.arraycopy(data, 0, newData, 0, i);
            else
                System.arraycopy(data, i+1, newData, 0, i+1);
            data = newData;
            dataCount--;
            return true;
        }
        else if(subset[i]!=null  && data[i]!=target)
        {
            boolean answer;
            answer = subset[i].looseRemove(target);
            if(subset[i].dataCount<MINIMUM)
            {
                fixShortage(i);
            }
            return answer;
        }
        else if(subset[i]!=null && data[i]==target)
        {
            data[i] = subset[i].removeBiggest();
            if(subset[i].dataCount < MINIMUM)
                fixShortage(i);
            return true;
        }
        else
            return false;
    }
    
    private void fixShortage(int i)
    {
        if(i>0)
        {
            //Case 1: Transfer an extra element from subset[i-1].
            if(subset[i-1].dataCount>MINIMUM)
            {
                subset[i].data[subset[i].dataCount] = data[i-1];
                subset[i].dataCount++;
                data[i-1] = subset[i-1].data[subset[i-1].dataCount-1];
                int[] newData = new int[3];
                System.arraycopy(subset[i-1].data, 0, newData, 0, 1);
                subset[i-1].data = newData;
                subset[i-1].dataCount--;
                if(!(subset[i-1].isLeaf()))
                {
                    subset[i].subset[subset[i].childCount] = new IntBalancedSet();
                    for(int p=subset[i].childCount;p>0;p--)
                    {
                        subset[i].subset[p] = subset[i].subset[p-1];
                    }
                    subset[i].subset[0] = subset[i-1].subset[subset[i-1].childCount-1];
                    subset[i-1].subset[subset[i-1].childCount-1] = null;
                    subset[i].childCount++;
                    subset[i-1].childCount--;
                }
            }
            
            
            //Case 3: Combine subset[i] with subset[i-1]
            else if(subset[i-1].dataCount==MINIMUM)
            {
                subset[i-1].data[subset[i-1].dataCount] = data[i-1];
                int[] newData = new int[3];
                if(data[0]==data[i-1])
                    System.arraycopy(data, 1, newData, 0, dataCount-1);
                else
                    System.arraycopy(data, 0, newData, 0, dataCount-1);
                data = newData;
                dataCount--;
                subset[i-1].dataCount++;
                if(!subset[i-1].isLeaf())
                {
                    subset[i-1].subset[subset[i-1].childCount] = new IntBalancedSet();
                    subset[i-1].subset[subset[i-1].childCount] = subset[i].subset[0];
                
                    subset[i-1].childCount++;
                }
                
                if(i==2)
                {
                    subset[i] = null;
                }
                else
                {
                    subset[i] = subset[i+1];
                    subset[i+1]=subset[i+2];
                }
                
                childCount--;
            }
        }
        else
        {
            //Case 2: Transfer an extra element from subset[i+1].
            if(i==0 && subset[i+1].dataCount>MINIMUM)
            {
                subset[i].dataCount++;
                subset[i].data[subset[i].dataCount-1] = data[i];
                data[i] = subset[i+1].data[0];
                int[] newData = new int[3];
                System.arraycopy(subset[i+1].data, 1, newData, 0, 1);
                subset[i+1].data = newData;
                subset[i+1].dataCount--;
                if(!(subset[i+1].isLeaf()))
                {
                    subset[i].subset[subset[i].childCount] = new IntBalancedSet();
                    subset[i].subset[subset[i].childCount] = subset[i+1].subset[0];
                    for(int p=0;p<subset[i].childCount;p++)
                    {
                        subset[i+1].subset[p] = subset[i+1].subset[p+1];
                    }
                    subset[i].childCount++;
                    subset[i+1].childCount--;
                }
            }
            //Case 4: Combine subset[i] with subset[i+1].
            else if(i==0 && subset[i+1].dataCount==MINIMUM)
            {
                int[] newData = new int[3];
                System.arraycopy(subset[i+1].data, 0, newData, 1, 1);
                subset[i+1].data = newData;
                subset[i+1].data[0] = data[0];
                subset[i+1].dataCount++;
                data[0]=data[1];
                data[1]=data[2];
                dataCount--;
                if(!subset[i+1].isLeaf())
                {
                    subset[i+1].subset[subset[i+1].childCount] = new IntBalancedSet();
                    for(int p=subset[i+1].childCount; p>0; p--)
                    {
                        subset[i+1].subset[p] = subset[i+1].subset[p-1];
                    }
                    subset[i+1].subset[0] = subset[i].subset[0];
                
                    subset[i+1].childCount++;
                }
                
                subset[i] = subset[i+1];
                subset[i+1]=subset[i+2];
                subset[i+2] = subset[i+3];
                childCount--;
            }
        }         
    }
    
    private int removeBiggest()
    {
        int answer;
        if(isLeaf())
        {
            answer = data[--dataCount];
            return answer;
        }
        else
        {
            answer = subset[childCount-1].removeBiggest();
            if(subset[childCount-1].dataCount<MINIMUM)
                fixShortage(childCount-1);
            return answer;
        }
    }
    
    public void print(int indent)
    {
        final int EXTRA_INDENT = 4;
        int i;
        int space;
        
        for(space=0; space<indent; space++)
        {
            System.out.print(" ");
        }
        for(i=0; i<dataCount; i++)
        {
            System.out.print(data[i] + " ");
        }
        System.out.println();
        
        for(i=0; i<childCount; i++)
        {
            subset[i].print(indent + EXTRA_INDENT);
        }
    }
}